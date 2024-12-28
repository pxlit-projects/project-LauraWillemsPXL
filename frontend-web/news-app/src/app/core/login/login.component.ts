import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../shared/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);
  firstName: string = "";
  lastName: string = "";
  userRole: string = "";

  login(): void {
    this.authService.setUserName(this.firstName, this.lastName);
    this.authService.setUserRole(this.userRole);
    this.router.navigate(['/posts']);
  }
}
