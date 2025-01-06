import {Component, inject, Input, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {Post} from "../../shared/model/post.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {DatePipe} from "@angular/common";
import {AuthService} from "../../shared/services/auth.service";
import {FormsModule} from "@angular/forms";
import {ReviewService} from "../../shared/services/review.service";
import {RejectPostRequest} from "../../shared/model/rejectPostRequest.model";

@Component({
  selector: 'app-post-details',
  standalone: true,
  imports: [
    NavbarComponent,
    DatePipe,
    FormsModule
  ],
  templateUrl: './post-details.component.html',
  styleUrl: './post-details.component.css'
})
export class PostDetailsComponent implements OnInit {
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  postService: PostService = inject(PostService);
  reviewService: ReviewService = inject(ReviewService);
  authService: AuthService = inject(AuthService);
  postId!: number;
  post!: Post;
  role!: string;
  review!: string;
  rejectionComment!: string;

  ngOnInit(): void {
    this.role = this.authService.getUserRole();
    this.postId = this.activatedRoute.snapshot.params['id'];
    this.postService.getPostById(this.postId).subscribe(post => {
      this.post = post;
    });
  }

  uploadReview() {
    if (this.review.toLowerCase() == "approve") {
      this.reviewService.approvePost(this.postId).subscribe(() => {
        this.router.navigate(['/posts']);
      });
    } else {
      this.reviewService.rejectPost(this.postId, new RejectPostRequest(this.rejectionComment)).subscribe(() => {
        this.router.navigate(['/posts']);
      });
    }
  }
}
