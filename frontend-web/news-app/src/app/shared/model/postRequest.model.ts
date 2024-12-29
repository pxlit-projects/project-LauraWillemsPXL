export class PostRequest {
  title: string;
  content: string;
  tags: string[];
  author: string;
  draft: boolean;

  constructor(title: string, content: string, tags: string[], author: string, draft: boolean) {
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.author = author;
    this.draft = draft;
  }
}
