import { ComponentFixture, TestBed } from "@angular/core/testing";
import {PostCardComponent} from "./post-card.component";

describe("PostCardComponent", () => {
  let component: PostCardComponent;
  let fixture: ComponentFixture<PostCardComponent>;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [PostCardComponent],
    });

    fixture = TestBed.createComponent(PostCardComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
