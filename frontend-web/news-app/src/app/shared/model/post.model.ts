export class Post {
  id: number;
  title: string;
  content: string;
  tags: string[];
  author: string;
  publishedDate: Date;
  draft: boolean;
  status: string;
  rejectionComment?: string;

  constructor(id: number, title: string, content: string, tags: string[], author: string, publishedDate: Date, draft: boolean, status: string, rejectionComment?: string) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.author = author;
    this.publishedDate = publishedDate;
    this.draft = draft;
    this.status = status;
    this.rejectionComment = rejectionComment;
  }
}
