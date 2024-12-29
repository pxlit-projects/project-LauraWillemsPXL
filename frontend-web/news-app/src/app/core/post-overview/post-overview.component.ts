import {Component, inject, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {MatCardModule} from "@angular/material/card";
import {Router} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {Post} from "../../shared/model/post.model";
import {MatChipsModule} from "@angular/material/chips";
import {DatePipe} from "@angular/common";
import {PostCardComponent} from "../post-card/post-card.component";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-post-overview',
  standalone: true,
  imports: [
    NavbarComponent,
    MatCardModule,
    MatChipsModule,
    DatePipe,
    PostCardComponent
  ],
  templateUrl: './post-overview.component.html',
  styleUrl: './post-overview.component.css'
})
export class PostOverviewComponent implements OnInit{
  router: Router = inject(Router);
  postService: PostService= inject(PostService);
  authService: AuthService = inject(AuthService);
  posts: Post[] = [];
  role!: string;

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(response => {
      this.posts = response;
    })

    this.role = this.authService.getUserRole();
  }

  onAddPostClick(): void {
    this.router.navigate(['/posts/addPost']);
  }
}
