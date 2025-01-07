export class Comment {
  id: number;
  postId: number;
  comment: string;
  createdBy: string;
  createdAt: Date;

  constructor(id: number, postId: number, comment: string, createdBy: string, createdAt: Date) {
    this.id = id;
    this.postId = postId;
    this.comment = comment;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }
}
