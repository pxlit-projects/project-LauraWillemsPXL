import {Component, inject, Input} from '@angular/core';
import {Router} from "@angular/router";
import {MatCardModule} from "@angular/material/card";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe
  ],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.css'
})
export class PostCardComponent {
  router: Router = inject(Router);
  @Input() id!: number;
  @Input() title!: string;
  @Input() content!: string;
  @Input() tags!: string[];
  @Input() author!: string;
  @Input() publishedDate!: Date;
  @Input() isDraft!: boolean;

  onEditClick() {
    this.router.navigate([`/posts/drafts/${this.id}`]);
  }
}
