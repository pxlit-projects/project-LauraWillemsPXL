export class CommentRequest {
  postId: number;
  comment: string;
  createdBy: string;

  constructor(postId: number, comment: string, createdBy: string) {
    this.postId = postId;
    this.comment = comment;
    this.createdBy = createdBy;
  }
}
