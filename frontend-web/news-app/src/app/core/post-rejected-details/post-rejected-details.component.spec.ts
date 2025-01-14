import { ComponentFixture, TestBed } from "@angular/core/testing";
import {PostRejectedDetailsComponent} from "./post-rejected-details.component";
import {PostService} from "../../shared/services/post.service";
import {ReviewService} from "../../shared/services/review.service";
import {ActivatedRoute} from "@angular/router";

describe("PostRejectedDetailsComponent", () => {
  let component: PostRejectedDetailsComponent;
  let fixture: ComponentFixture<PostRejectedDetailsComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let reviewServiceMock: jasmine.SpyObj<ReviewService>;

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getPostById"]);
    reviewServiceMock = jasmine.createSpyObj("ReviewService", ["getRejectionCommentOfPost"]);

    TestBed.configureTestingModule({
      imports: [PostRejectedDetailsComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: ReviewService, useValue: reviewServiceMock },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => 1 } } } },
      ],
    });

    fixture = TestBed.createComponent(PostRejectedDetailsComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
