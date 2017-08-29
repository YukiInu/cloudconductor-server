import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Observable } from 'rxjs/Observable';

import { HTTPService } from './abstract.http.service';

/**
 * Copyright 2017 Cinovo AG<br>
 * <br>
 *
 * @author mweise
 */
export interface ConfigFile {
  name: string,
  pkg: string,
  targetPath: string,
  owner: string,
  group: string,
  fileMode: string,
  isDirectory: boolean,
  isTemplate: boolean,
  isReloadable: boolean,
  checksum?: string,
  dependentServices: string[]
  templates: string[]
}

@Injectable()
export class FileHttpService extends HTTPService {

  constructor(protected http: Http) {
    super(http);
    this.basePathURL = 'file/'
  }

  public getFiles(): Observable<ConfigFile[]> {
    return this._get('');
  }

  public getFilesForTemplate(templateName: string): Observable<ConfigFile[]> {
    return this._get(`template/${templateName}`);
  }

  public updateFile(updatedFile: ConfigFile): Observable<boolean> {
    updatedFile['@class'] = 'de.cinovo.cloudconductor.api.model.ConfigFile';
    return this._put('', updatedFile);
  }

  public getFile(fileName: string): Observable<ConfigFile> {
    return this._get(fileName);
  }

  public deleteFile(fileName: string): Observable<boolean> {
    return this._delete(fileName);
  }

  public getFileData(fileName: string): Observable<string> {
    return this._get(`${fileName}/data`);
  }

  public updateFileData(fileName: string, fileData: string): Observable<boolean> {
    return this._put(`${fileName}/data`, fileData);
  }

}
