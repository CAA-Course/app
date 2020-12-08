import { Component, ChangeDetectorRef } from '@angular/core';
import { Credentials } from '../core/models/credentials.model';
import { Store } from '@ngrx/store';
// import { Observable } from 'rxjs';
// import { selectAuthState } from '../store/app.states';
// import { LogIn } from '../store/actions/user.actions';
// import { AuthState } from '../store/states/auth.states';
import {
  onAuthUIStateChange,
  CognitoUserInterface,
  AuthState,
} from '@aws-amplify/ui-components';
import { LogInSuccess } from '../store/actions/user.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  credentials: Credentials = new Credentials();
  user: CognitoUserInterface | undefined;
  authState: AuthState;

  constructor(
    private ref: ChangeDetectorRef,
    private store: Store<AuthState>
  ) {}

  ngOnInit() {
    onAuthUIStateChange((authState, authData) => {
      this.authState = authState;
      this.user = authData as CognitoUserInterface;
      console.log(this.user);
      if (this.user) {
        this.store.dispatch(new LogInSuccess(this.user));
      }
      this.ref.detectChanges();
    });
  }

  ngOnDestroy() {
    return onAuthUIStateChange;
  }
}
