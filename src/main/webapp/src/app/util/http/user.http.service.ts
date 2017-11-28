import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

/**
 * Copyright 2017 Cinovo AG<br>
 * <br>
 *
 * @author mweise
 */
export interface User {
  loginName: string,
  displayName: string,
  registrationDate?: Date,
  active?: boolean,
  email: string,
  password?: string,
  userGroups?: string[],
  agents?: string[],
  authTokens?: AuthToken[]
}

export interface AuthToken {
  token: string,
  creationDate: Date,
  revoked: Date
}

@Injectable()
export class UserHttpService {

  public static emptyUser: User = {loginName: '', displayName: '', email: '', registrationDate: new Date(), active: false,
  userGroups: [], agents: [], authTokens: []};

  private _basePath = 'api/user';

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this._basePath)
  }

  public getUser(username: string): Observable<User> {
    return this.http.get<User>(`${this._basePath}/${username}`)
                              .map(u => Object.assign(UserHttpService.emptyUser, u));
  }

  public saveUser(userToSave: User): Observable<boolean> {
    userToSave['@class'] = 'de.cinovo.cloudconductor.api.model.User';
    return this.http.put<boolean>(this._basePath, userToSave);
  }

  public deleteUser(username: string): Observable<boolean> {
    return this.http.delete<boolean>(`${this._basePath}/${username}`);
  }

  public createAuthToken(username: string): Observable<User> {
    return this.http.post<User>(`${this._basePath}/${username}/authtoken`, {});
  }

  public revokeAuthToken(username: string, authToken: AuthToken): Observable<User> {
    return this.http.delete<User>(`${this._basePath}/${username}/authtoken/${authToken.token}`);
  }

}
