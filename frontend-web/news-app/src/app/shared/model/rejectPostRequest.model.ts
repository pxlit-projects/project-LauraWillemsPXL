export class RejectPostRequest {
  rejectionComment: string;

  constructor(rejectionComment: string) {
    this.rejectionComment = rejectionComment;
  }
}
