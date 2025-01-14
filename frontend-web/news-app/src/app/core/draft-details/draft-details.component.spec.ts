import { ComponentFixture, TestBed } from "@angular/core/testing";
import {DraftDetailsComponent} from "./draft-details.component";
import {PostService} from "../../shared/services/post.service";
import {AuthService} from "../../shared/services/auth.service";
import {ActivatedRoute} from "@angular/router";

describe("DraftDetailsComponent", () => {
  let component: DraftDetailsComponent;
  let fixture: ComponentFixture<DraftDetailsComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getPostById", "updatePost", "addPost"]);
    authServiceMock = jasmine.createSpyObj("AuthService", ["getUserName"]);

    TestBed.configureTestingModule({
      imports: [DraftDetailsComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => 1 } } } },
      ]
    });

    fixture = TestBed.createComponent(DraftDetailsComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });
});
