import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  router: Router = inject(Router);
  authService: AuthService = inject(AuthService);
  userRole!: string;

  ngOnInit(): void {
    this.userRole = this.authService.getUserRole();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  onPostsClick() : void {
    this.router.navigate(['/posts']);
  }

  onDraftsClick(): void {
    this.router.navigate(['/drafts']);
  }

  onNotificationsClick(): void {
    this.router.navigate(['/notifications']);
  }
}
