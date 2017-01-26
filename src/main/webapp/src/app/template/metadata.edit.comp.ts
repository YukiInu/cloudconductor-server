import { Component, AfterViewInit, Input, Output, EventEmitter } from "@angular/core";
import { Template, TemplateHttpService } from "../services/http/template.http.service";
import { Settings, SettingHttpService } from "../services/http/setting.http.service";
import { Repo, RepoHttpService } from "../services/http/repo.http.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Validator } from "../util/validator.util";
import { Sorter } from "../util/sorters.util";
import { Observable } from "rxjs";
import { AlertService } from "../services/alert/alert.service";
import { Mode } from "../util/enums.util";

/**
 * Copyright 2017 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Component({
  moduleId: module.id,
  selector: 'template-metadata-edit',
  templateUrl: 'html/metadata.edit.comp.html'
})
export class TemplateMetaDataEdit implements AfterViewInit {

  @Input() obsTemplate: Observable<Template>;
  @Input() mode: Mode = Mode.EDIT;
  @Output() reloadTrigger: EventEmitter<any> = new EventEmitter();

  modes = Mode;
  private template: Template = {name: "", description: ""};

  private existingTemplateNames: Array<string> = [];

  private settings: Settings = {};

  protected allRepos: Array<Repo> = [];
  private newRepo: string = "";
  private showNewRepo: boolean = false;

  private copyFrom: string = "";

  private back: any;

  constructor(private route: ActivatedRoute, private settingsHttp: SettingHttpService,
              private templateHttp: TemplateHttpService, private repoHttp: RepoHttpService,
              private alerts: AlertService, private router: Router) {
  };

  ngAfterViewInit(): void {
    this.obsTemplate.subscribe((result) => this.template = result);

    this.route.queryParams.subscribe((params) => {
      this.back = {ret: params['ret'], id: params['id']};
    });

    this.settingsHttp.getSettings().subscribe(
      (result) => this.settings = result
    );

    if (this.mode == Mode.NEW) {
      this.templateHttp.getTemplates().subscribe((result) => {
        for (let t of result) {
          this.existingTemplateNames.push(t.name);
        }
        this.existingTemplateNames.sort();
      })
    }
  }

  protected saveTemplateMetadata(): void {
    if (!this.isNameValid()) {
      this.alerts.danger("There already exists a template with the name " + this.template.name);
      return;
    }

    if (this.mode == Mode.NEW && Validator.notEmpty(this.copyFrom)) {
      this.templateHttp.getTemplate(this.copyFrom).subscribe(
        (result) => {
          this.template.repos = result.repos;
          this.template.versions = result.versions;
          this.templateHttp.save(this.template).subscribe(
            () => {
              this.alerts.success("Successfully saved the template " + this.template.name);
              this.router.navigate(['template', this.template.name]);
            },
            () => this.alerts.danger("Failed to save template " + this.copyFrom)
          )
        },
        () => this.alerts.danger("Failed to copy repositories and packages from template " + this.copyFrom)
      )
    } else {
      this.templateHttp.save(this.template).subscribe(
        () => {
          this.alerts.success("Successfully saved the template " + this.template.name);
          if (this.mode == Mode.EDIT) {
            this.reloadTrigger.emit(true);
            return;
          }
          this.router.navigate(['template', this.template.name]);
        },
        () => this.alerts.danger("Failed to save template " + this.copyFrom)
      )
    }
  }

  protected deleteTemplate(): void {
    this.templateHttp.deleteTemplate(this.template).subscribe(
      () => {
        this.alerts.success("Successfully delete the template: " + this.template.name);
        this.router.navigate(['template']);
      },
      () => {
        this.alerts.danger("Failed to delete the template: " + this.template.name);
      }
    )
  }

  protected addNewRepo(): void {
    if (this.newRepo) {
      this.template.repos.push(this.newRepo);
      this.template.repos.sort();
      this.settings.disallowUninstall.sort();
      this.newRepo = null;
      this.showNewRepo = false;
    }
  }

  protected removeRepo(repoName: string): void {
    if (Validator.notEmpty(repoName)) {
      this.template.repos.splice(this.template.repos.indexOf(repoName), 1);
      if(this.showNewRepo) {
        this.goToAddRepo();
      }
    }
  }

  protected goToAddRepo(): void {
    this.newRepo = "";
    this.showNewRepo = true;
    this.repoHttp.getRepos().subscribe(
      (result) => {
        this.allRepos = result.filter(
          (repo) => {
            return !this.template.repos.includes(repo.name)
          }
        ).sort(Sorter.repo);
        if(this.allRepos.length > 0) {
          this.newRepo = this.allRepos[0].name;
        }
      }
    )
  }

  public isNameValid(): boolean {
    if (!Validator.notEmpty(this.template.name)) {
      return false;
    }
    if (this.template.name.trim() == "new") {
      return false;
    }
    if (this.mode == Mode.EDIT) {
      return true;
    }
    return this.existingTemplateNames.indexOf(this.template.name.trim()) < 0;

  }
}
