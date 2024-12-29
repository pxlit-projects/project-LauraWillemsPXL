export class PostRequest {
  title: string;
  content: string;
  author: string;
  isDraft: boolean;

  constructor(title: string, content: string, author: string, isDraft: boolean) {
    this.title = title;
    this.content = content;
    this.author = author;
    this.isDraft = isDraft;
  }
}
