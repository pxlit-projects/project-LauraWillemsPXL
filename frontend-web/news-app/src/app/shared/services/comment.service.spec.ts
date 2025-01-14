import {CommentService} from "./comment.service";
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {Comment} from "../model/comment.model";
import {TestBed} from "@angular/core/testing";
import {provideHttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {CommentRequest} from "../model/commentRequest.model";

describe("CommentService", () => {
  let service: CommentService;
  let httpTestingController: HttpTestingController;

  const mockComments: Comment[] = [
    new Comment(1, 1, "Comment 1", "John Doe", new Date()),
    new Comment(2, 1, "Comment 2", "Jane Doe", new Date()),
    new Comment(3, 2, "Comment 3", "Alice Smith", new Date())
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CommentService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(CommentService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it("should add comment via POST", () => {
    const commentRequest = new CommentRequest(1, "Comment4", "Emiel Willems");
    const newComment = new Comment(4, 1, "Comment 4", "Laura Willems", new Date());

    service.addComment(commentRequest).subscribe(comment => {
      expect(comment).toEqual(newComment);
    });

    const req = httpTestingController.expectOne(`${environment.api}/comment/api/comment`);
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(commentRequest);
    req.flush(newComment);
  });

  it("should retrieve comments via GET", () => {
    service.getAllComments().subscribe(comments => {
      expect(comments).toEqual(mockComments)
    });

    const req = httpTestingController.expectOne(`${environment.api}/comment/api/comment`);
    expect(req.request.method).toBe("GET");
    req.flush(mockComments);
  });

  it("should update comment via PUT", () => {
    const updatedComment = { ...mockComments[0], content: "Updated content" };

    service.updateComment(updatedComment.id, updatedComment).subscribe(comment => {
      expect(comment).toEqual(updatedComment);
    });

    const req = httpTestingController.expectOne(`${environment.api}/comment/api/comment/update/${updatedComment.id}`);
    expect(req.request.method).toBe("PUT");
    expect(req.request.body).toEqual(updatedComment);
    req.flush(updatedComment);
  });

  it("should delete comment via DELETE", () => {
    const commentId = 1;

    service.deleteComment(commentId).subscribe(() => {
      expect().nothing();
    });

    const req = httpTestingController.expectOne(`${environment.api}/comment/api/comment/delete/${commentId}`);
    expect(req.request.method).toBe("DELETE");
    req.flush(null);
  });
});
