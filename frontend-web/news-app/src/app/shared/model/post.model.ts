export class Post {
  id: number;
  title: string;
  content: string;
  tags: string[];
  author: string;
  publishedDate: Date;
  draft: boolean;


  constructor(id: number, title: string, content: string, tags: string[], author: string, publishedDate: Date, draft: boolean) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.author = author;
    this.publishedDate = publishedDate;
    this.draft = draft;
  }
}
