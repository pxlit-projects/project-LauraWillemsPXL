import {Component, inject, Input, OnInit} from '@angular/core';
import {CommentService} from "../../shared/services/comment.service";
import {Comment} from "../../shared/model/comment.model";
import {MatCardModule} from "@angular/material/card";
import {DatePipe} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {CommentRequest} from "../../shared/model/CommentRequest.model";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-post-comments',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe,
    FormsModule
  ],
  templateUrl: './post-comments.component.html',
  styleUrl: './post-comments.component.css'
})
export class PostCommentsComponent implements OnInit{
  commentService: CommentService = inject(CommentService);
  authService: AuthService = inject(AuthService);
  comments: Comment[] = [];
  comment: string = "";
  @Input() postId!: number;

  ngOnInit(): void {
    this.commentService.getAllComments().subscribe(response => {
      this.comments = response;
    });
  }

  onSubmit() {
    let commentRequest = new CommentRequest(this.postId, this.comment, this.authService.getUserName());

    this.commentService.addComment(commentRequest).subscribe(response => {
      this.comments.push(response);
    });

    this.comment = "";
  }
}
