import { ComponentFixture, TestBed } from "@angular/core/testing";
import {DraftOverviewComponent} from "./draft-overview.component";
import {PostService} from "../../shared/services/post.service";

describe("DraftOverviewComponent", () => {
  let component: DraftOverviewComponent;
  let fixture: ComponentFixture<DraftOverviewComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;


  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getAllDrafts"]);

    TestBed.configureTestingModule({
      imports: [DraftOverviewComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
      ]
    });

    fixture = TestBed.createComponent(DraftOverviewComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
