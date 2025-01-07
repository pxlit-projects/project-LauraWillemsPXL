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
  userName!: string;
  comment: string = "";
  onEditComment: boolean = false;
  editCommentId!: number;
  editedComment: string = "";
  @Input() postId!: number;

  ngOnInit(): void {
    this.commentService.getAllComments().subscribe(response => {
      this.comments = response;
    });

    this.userName = this.authService.getUserName();
  }

  onSubmit() {
    let commentRequest = new CommentRequest(this.postId, this.comment, this.userName);

    this.commentService.addComment(commentRequest).subscribe(response => {
      this.comments.push(response);
    });

    this.comment = "";
  }

  onEdit(commentId: number, comment: string) {
    this.onEditComment = !this.onEditComment;
    this.editCommentId = commentId;
    this.editedComment = comment;
  }

  onDelete(commentId: number) {
    this.commentService.deleteComment(commentId).subscribe(() => {
      this.comments = this.comments.filter(comment => comment.id !== commentId);
    });
  }

  onUpdateComment() {
    let commentRequest = new CommentRequest(this.postId, this.editedComment, this.userName);

    this.commentService.updateComment(this.editCommentId, commentRequest).subscribe(response => {
      this.comments = this.comments.filter(comment => comment.id !== response.id);
      this.comments.push(response);
    });

    this.onEditComment = !this.onEditComment;
    this.editedComment = "";
    this.editCommentId = 0;
  }

  cancelUpdateComment() {
    this.onEditComment = !this.onEditComment;
    this.editedComment = "";
    this.editCommentId = 0;
  }
}
