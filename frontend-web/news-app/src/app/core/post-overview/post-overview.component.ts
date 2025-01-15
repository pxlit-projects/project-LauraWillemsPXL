import {Component, inject, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {MatCardModule} from "@angular/material/card";
import {Router} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {Post} from "../../shared/model/post.model";
import {MatChipsModule} from "@angular/material/chips";
import {DatePipe, NgIf} from "@angular/common";
import {PostCardComponent} from "../post-card/post-card.component";
import {AuthService} from "../../shared/services/auth.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-post-overview',
  standalone: true,
  imports: [
    NavbarComponent,
    MatCardModule,
    MatChipsModule,
    DatePipe,
    PostCardComponent,
    FormsModule,
    NgIf
  ],
  templateUrl: './post-overview.component.html',
  styleUrl: './post-overview.component.css'
})
export class PostOverviewComponent implements OnInit{
  router: Router = inject(Router);
  postService: PostService= inject(PostService);
  authService: AuthService = inject(AuthService);
  posts: Post[] = [];
  filteredPosts: Post[] = [];
  role!: string;
  authorName: string = "";
  tag: string = "";
  publishedDate: Date | null = new Date();
  tags: string[] = [];

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(response => {
      this.posts = response;
      this.filteredPosts = response;
      this.getAllTags();
    })

    this.role = this.authService.getUserRole();
  }

  getAllTags(): void {
    this.posts.forEach(post => {
      post.tags.forEach(tag => {
        if (!this.tags.includes(tag)) {
          this.tags.push(tag);
        }
      });
    });
  }

  filterPosts(): Post[] {
    let authorName = this.authorName ? this.authorName.toLowerCase() : "";
    let tag = this.tag ? this.tag.toLowerCase() : "";
    let publishedDate = this.publishedDate;

    this.filteredPosts = this.posts.filter(post => {
      let matchesAuthor = !authorName || post.author.toLowerCase().includes(authorName);
      let matchesTag = !tag || post.tags.some(t => t.toLowerCase() === tag);
      let matchesDate = !publishedDate ||
        new Date(post.publishedDate).toDateString() === new Date(publishedDate).toDateString();

      return matchesAuthor && matchesTag && matchesDate;
    });

    return this.filteredPosts;
  }

  clearFilters(): void {
    this.authorName = "";
    this.tag = "";
    this.publishedDate = null;
    this.filterPosts();
  }

  onAddPostClick(): void {
    this.router.navigate(['/posts/addPost']);
  }

  onPostClick(id: number): void {
    this.router.navigate([`/posts/${id}/details`]);
  }
}
