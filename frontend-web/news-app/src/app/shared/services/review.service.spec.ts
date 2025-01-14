import {ReviewService} from "./review.service";
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {provideHttpClient} from "@angular/common/http";
import {TestBed} from "@angular/core/testing";
import {environment} from "../../../environments/environment";
import {RejectPostRequest} from "../model/rejectPostRequest.model";
import {RejectionReview} from "../model/rejectionReview.model";

describe("ReviewService", () => {
  let service: ReviewService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReviewService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });
    service = TestBed.inject(ReviewService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it("should approve post via POST", () => {
    const postId = 1;

    service.approvePost(postId).subscribe(() => {
      expect().nothing();
    });

    const req = httpTestingController.expectOne(`${environment.api}/review/api/review/approve/${postId}`);
    expect(req.request.method).toBe("POST");
    req.flush(null);
  });

  it("should reject post via POST", () => {
    const postId = 1;
    const rejectPostRequest = new RejectPostRequest("The content contains a lot of grammatical errors.");

    service.rejectPost(postId, rejectPostRequest).subscribe(() => {
      expect().nothing();
    });

    const req = httpTestingController.expectOne(`${environment.api}/review/api/review/reject/${postId}`);
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(rejectPostRequest);
    req.flush(null);
  });

  it("should retrieve rejection comment of post via GET", () => {
    const postId = 1;
    const rejectionReview: RejectionReview = new RejectionReview("The content contains a lot of grammatical errors.");

    service.getRejectionCommentOfPost(postId).subscribe((review) => {
      expect(review).toEqual(rejectionReview);
    });

    const req = httpTestingController.expectOne(`${environment.api}/review/api/review/${postId}`);
    expect(req.request.method).toBe("GET");
    req.flush(rejectionReview);
  });
});
