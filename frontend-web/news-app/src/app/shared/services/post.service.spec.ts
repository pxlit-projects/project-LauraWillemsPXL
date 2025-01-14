import {PostService} from "./post.service";
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {Post} from "../model/post.model";
import {TestBed} from "@angular/core/testing";
import {provideHttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.development";
import {PostRequest} from "../model/postRequest.model";

describe("PostService", () => {
  let service: PostService;
  let httpTestingController: HttpTestingController;

  const mockPosts: Post[] = [
    new Post(1, "Post 1", "Content of post 1", ["tag1", "tag2"], "Laura Willems", new Date(), false, "approved"),
    new Post(2, "Post 2", "Content of post 2", ["tag1", "tag2"], "Sara Willems", new Date(), false, "approved"),
    new Post(1, "Post 3", "Content of post 3", ["tag1"], "Laura Willems", new Date(), false, "approved"),
  ];

  const mockDrafts: Post[] = [
    new Post(1, "Post 1", "Content of post 1", ["tag1", "tag2"], "Laura Willems", new Date(), true, "approved"),
    new Post(2, "Post 2", "Content of post 2", ["tag1", "tag2"], "Sara Willems", new Date(), true, "approved"),
    new Post(1, "Post 3", "Content of post 3", ["tag1"], "Laura Willems", new Date(), true, "approved"),
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PostService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(PostService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it("should add post via POST", () => {
    const postRequest = new PostRequest("Post 4", "Content of post 4", ["tag1", "tag2"], "Laura Willems", false);
    const newPost = new Post(4, "Post 4", "Content of post 4", ["tag1", "tag2"], "Laura Willems", new Date(), false, "pending");

    service.addPost(postRequest).subscribe(post => {
      expect(post).toEqual(newPost);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post`);
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(postRequest);
    req.flush(newPost);
  });

  it("should retrieve all posts via GET", () => {
    service.getAllPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post`);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });

  it("should retrieve post by id via GET", () => {
    const postId = 1;

    service.getPostById(postId).subscribe(post => {
      expect(post).toEqual(mockPosts[0]);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post/${postId}`);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts[0]);
  });

  it("should retrieve all drafts via GET", () => {
    service.getAllDrafts().subscribe(drafts => {
      expect(drafts).toEqual(mockDrafts);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post/drafts`);
    expect(req.request.method).toBe("GET");
    req.flush(mockDrafts);
  });

  it("should update post via PUT", () => {
    const updatedPost = { ...mockPosts[0], title: "Updated Title" };

    service.updatePost(updatedPost.id, updatedPost).subscribe(post => {
      expect(post).toEqual(updatedPost);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post/update/${updatedPost.id}`);
    expect(req.request.method).toBe("PUT");
    expect(req.request.body).toEqual(updatedPost);
    req.flush(updatedPost);
  });

  it("should delete draft via DELETE", () => {
    const draftId = 1;

    service.deleteDraft(draftId).subscribe(() => {
      expect().nothing();
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post/draft/${draftId}`);
    expect(req.request.method).toBe("DELETE");
    req.flush({});

  });

  it("should retrieve notifications of author via GET", () => {
    service.getNotificationsOfAuthor().subscribe(notifications => {
      expect(notifications).toEqual(["notification1", "notification2"]);
    });

    const req = httpTestingController.expectOne(`${environment.api}/post/api/post/notifications`);
    expect(req.request.method).toBe("GET");
    req.flush(["notification1", "notification2"]);
  });
});
