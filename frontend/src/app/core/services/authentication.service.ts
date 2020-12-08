import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Credentials } from '../models/credentials.model';
import { environment } from '../../../environments/environment';
import { Auth } from 'aws-amplify';
import { CognitoUser } from '@aws-amplify/auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private user: User;

  constructor(private http: HttpClient) {}

  public get currentUser(): User {
    return this.user;
  }

  login(credentials: Credentials) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });
    const params = new HttpParams({ fromObject: credentials as any });

    return this.http
      .post<any>(`${environment.apiUrl}/auth/login`, params.toString(), {
        headers,
      })
      .pipe(
        map((authResult) => {
          const user: User = {
            username: authResult.username,
            roles: [],
            firstName: authResult.firstName,
            id: authResult.id,
            lastName: authResult.lastName,
          };
          this.user = user as any;
          return user;
        })
      );
  }

  logout() {
    this.user = null;
  }

  public isAdmin() {
    // return this.user.roles.includes('admin');
    return true;
  }

  public isCustomer() {
    // return this.user.roles.includes('customer');
    return true;
  }

  public get isLogged() {
    return this.user != null;
  }

  public async loginWithCognito(credentials: Credentials) {
    try {
      const user: CognitoUser = await Auth.signIn(
        credentials.username,
        credentials.password
      );

      console.log(user);

      // user.attr

      // user.completeNewPasswordChallenge(credentials.password, );

      // user.getUserAttributes((attributes) => console.log(attributes));

      this.user = {
        firstName: '',
        id: 1234,
        roles: [],
        lastName: '',
        username: credentials.username,
      };

      return new Observable((obs) => obs.next(this.user));
    } catch (error) {
      console.log(error);
      alert('User Authentication failed');
    }
  }
}
