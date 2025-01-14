import { ComponentFixture, TestBed } from "@angular/core/testing";
import {PostDetailsComponent} from "./post-details.component";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {AuthService} from "../../shared/services/auth.service";
import {ReviewService} from "../../shared/services/review.service";

describe("PostDetailsComponent", () => {
  let component: PostDetailsComponent;
  let fixture: ComponentFixture<PostDetailsComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let reviewServiceMock: jasmine.SpyObj<ReviewService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getPostById"]);
    reviewServiceMock = jasmine.createSpyObj("ReviewService", ["approvePost", "rejectPost"]);

    TestBed.configureTestingModule({
      imports: [PostDetailsComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: ReviewService, useValue: reviewServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => 1 } } } },
      ]
    });

    fixture = TestBed.createComponent(PostDetailsComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
