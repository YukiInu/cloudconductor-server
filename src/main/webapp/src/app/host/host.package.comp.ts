import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';

import { Observable, Subscription } from 'rxjs/Rx';

import { Host } from '../util/http/host.http.service';
import { SettingHttpService } from '../util/http/setting.http.service';
import { Sorter } from '../util/sorters.util';
import { TemplateHttpService } from '../util/http/template.http.service';
import { PackageChange, PackageChangesService } from '../util/packagechanges/packagechanges.service';

/**
 * Copyright 2017 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Component({
  selector: 'host-packages',
  templateUrl: './host.package.comp.html'
})
export class HostPackages implements OnInit, OnDestroy {

  @Input() obsHost: Observable<Host>;

  public packages: PackageChange[] = [];
  public packageChanges = false;
  public host: Host = {name: '', template: ''};

  private hostSub: Subscription;
  private packageChangesSub: Subscription;

  constructor(private packageChangesService: PackageChangesService) { };

  public ngOnInit(): void {
    this.hostSub = this.obsHost.subscribe((newHost) => {
      this.loadPackages(newHost);
      this.host = newHost;
    });
  }

  private loadPackages(host: Host): void {
    this.packageChangesSub = this.packageChangesService.computePackageChanges(host).subscribe((packages) => {
       this.packages = packages;
       this.packageChanges = packages.some(pkg => (pkg.state !== 'protected' && pkg.state !== 'ok'));
    });
  }

  public ngOnDestroy(): void {
    if (this.hostSub) {
      this.hostSub.unsubscribe();
    }
    if (this.packageChangesSub) {
      this.packageChangesSub.unsubscribe();
    }
  }

}
