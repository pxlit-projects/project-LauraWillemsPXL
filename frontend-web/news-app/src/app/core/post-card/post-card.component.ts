import {Component, inject, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatCardModule} from "@angular/material/card";
import {DatePipe, NgClass} from "@angular/common";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe,
    NgClass
  ],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.css'
})
export class PostCardComponent implements OnInit {
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);
  @Input() id!: number;
  @Input() title!: string;
  @Input() content!: string;
  @Input() tags!: string[];
  @Input() author!: string;
  @Input() publishedDate!: Date;
  @Input() isDraft!: boolean;
  @Input() status!: string;
  userRole!: string;

  ngOnInit(): void {
    this.userRole = this.authService.getUserRole();
  }

  onEditClick() {
    this.router.navigate([`/drafts/${this.id}`]);
  }

  onRejectedPostEditClick(): void {
    this.router.navigate([`/posts/${this.id}/rejected/edit`]);
  }
}
