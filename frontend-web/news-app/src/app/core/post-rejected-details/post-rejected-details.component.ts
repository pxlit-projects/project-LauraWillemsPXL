import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NavbarComponent} from "../navbar/navbar.component";
import {Post} from "../../shared/model/post.model";
import {PostService} from "../../shared/services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PostRequest} from "../../shared/model/postRequest.model";
import {AuthService} from "../../shared/services/auth.service";
import {ReviewService} from "../../shared/services/review.service";
import {RejectionReview} from "../../shared/model/rejectionReview.model";

@Component({
  selector: 'app-post-rejected-details',
  standalone: true,
  imports: [
    FormsModule,
    NavbarComponent,
    ReactiveFormsModule
  ],
  templateUrl: './post-rejected-details.component.html',
  styleUrl: './post-rejected-details.component.css'
})
export class PostRejectedDetailsComponent implements OnInit{
  postId!: number;
  post!: Post;
  rejectionReview!: RejectionReview;
  postService: PostService = inject(PostService);
  reviewService: ReviewService = inject(ReviewService);
  authService: AuthService = inject(AuthService);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  fb: FormBuilder = inject(FormBuilder);
  postForm!: FormGroup;
  tags: string[] = [];

  ngOnInit(): void {
    this.postId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.reviewService.getRejectionCommentOfPost(this.postId).subscribe(response => {
      this.rejectionReview = response;
    });
    this.postService.getPostById(this.postId).subscribe(response => {
      this.post = response;
      this.tags = this.post.tags;
      this.initializeForm();
    });
  }

  initializeForm(): void {
    this.postForm = this.fb.group({
      title: [this.post.title, Validators.required],
      content: [this.post.content, Validators.required],
      tags: [this.post.tags, Validators.required],
    });
  }

  addTag(event: KeyboardEvent): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.trim();

    if ((event.key === ' ') && value) {
      if (!this.tags.includes(value)) {
        this.tags.push(value);
        this.postForm.get('tags')?.setValue(this.tags.join(','));
        input.value = '';
      }
    }
  }

  removeTag(tag: string): void {
    this.tags = this.tags.filter((t) => t !== tag);
    this.postForm.get('tags')?.setValue(this.tags.join(','));
  }

  onSubmit(): void {
    let title = this.postForm.get('title')?.value;
    let content = this.postForm.get('content')?.value;

    this.postService.updatePost(this.postId, new PostRequest(title, content, this.tags, this.authService.getUserName(), false)).subscribe(() => {
      this.router.navigate(['/posts']);
    });
  }
}
