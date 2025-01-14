import { PostOverviewComponent } from "./post-overview.component";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { PostService } from "../../shared/services/post.service";
import { Post } from "../../shared/model/post.model";
import { of } from "rxjs";
import {AuthService} from "../../shared/services/auth.service";

describe("PostOverviewComponent", () => {
  let component: PostOverviewComponent;
  let fixture: ComponentFixture<PostOverviewComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;
  const mockPosts: Post[] = [
    new Post(1, "Post 1", "Content of post 1", ["tag1", "tag2"], "Laura Willems", new Date(), false, "approved"),
    new Post(2, "Post 2", "Content of post 2", ["tag1", "tag2"], "Sara Willems", new Date(), false, "approved"),
    new Post(1, "Post 3", "Content of post 3", ["tag1"], "Laura Willems", new Date(), false, "approved"),
  ];

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getAllPosts"]);
    authServiceMock = jasmine.createSpyObj("AuthService", ["getUserRole"]);

    TestBed.configureTestingModule({
      imports: [PostOverviewComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: AuthService, useValue: authServiceMock }
      ]
    });

    fixture = TestBed.createComponent(PostOverviewComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });


  it("should initialize posts and filteredPosts on ngOnInit", () => {
    postServiceMock.getAllPosts.and.returnValue(of(mockPosts));

    component.ngOnInit();

    expect(component.posts).toEqual(mockPosts);
    expect(component.filteredPosts).toEqual(mockPosts);
  });

  it("should call getAllPosts on initialization", () => {
    postServiceMock.getAllPosts.and.returnValue(of(mockPosts));
    spyOn(component, "getAllTags");

    fixture.detectChanges();

    expect(component.getAllTags).toHaveBeenCalled();
  });

  it("should get all tags from posts", () => {
    component.posts = mockPosts;

    component.getAllTags();

    expect(component.tags).toEqual(["tag1", "tag2"]);
  });

  it("should filter posts based on the filter criteria", () => {
    component.posts = mockPosts;
    component.authorName = "Laura Willems";
    component.tag = "tag2";
    component.publishedDate = new Date();

    component.filterPosts();

    expect(component.filteredPosts).toEqual([mockPosts[0]]);
    expect(component.filteredPosts.length).toEqual(1);
  });

  it("should clear filters", () => {
    component.posts = mockPosts;
    component.authorName = "Laura Willems";
    component.tag = "tag2";

    component.clearFilters();

    expect(component.authorName).toEqual("");
    expect(component.tag).toEqual("");
    expect(component.filteredPosts).toEqual(mockPosts);
  });

  it("should navigate to add post page on onAddPostClick", () => {
    spyOn(component.router, "navigate");

    component.onAddPostClick();

    expect(component.router.navigate).toHaveBeenCalledWith(["/posts/addPost"]);
  });

  it("should navigate to post details page on onPostClick", () => {
    spyOn(component.router, "navigate");

    component.onPostClick(1);

    expect(component.router.navigate).toHaveBeenCalledWith(["/posts/1/details"]);
  });
});
