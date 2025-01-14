import { ComponentFixture, TestBed } from "@angular/core/testing";
import {PostCommentsComponent} from "./post-comments.component";
import {CommentService} from "../../shared/services/comment.service";
import {AuthService} from "../../shared/services/auth.service";

describe("PostCommentsComponent", () => {
  let component: PostCommentsComponent;
  let fixture: ComponentFixture<PostCommentsComponent>;
  let commentServiceMock: jasmine.SpyObj<CommentService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    commentServiceMock = jasmine.createSpyObj("CommentService", ["getAllComments", "addComment", "updateComment", "deleteComment"]);
    authServiceMock = jasmine.createSpyObj("AuthService", ["getUserName"]);

    TestBed.configureTestingModule({
      imports: [PostCommentsComponent],
      providers: [
        { provide: CommentService, useValue: commentServiceMock },
        { provide: AuthService, useValue: authServiceMock }
      ]
    });

    fixture = TestBed.createComponent(PostCommentsComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
