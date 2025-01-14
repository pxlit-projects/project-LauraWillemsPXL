import {AddPostFormComponent} from "./add-post-form.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PostService} from "../../shared/services/post.service";
import {Router} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {of} from "rxjs";
import {Post} from "../../shared/model/post.model";
import {PostRequest} from "../../shared/model/postRequest.model";

describe("AddPostFormComponent", () => {
  let component: AddPostFormComponent;
  let fixture: ComponentFixture<AddPostFormComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["addPost"]);
    routerMock = jasmine.createSpyObj("Router", ["navigate"]);

    TestBed.configureTestingModule({
      imports: [AddPostFormComponent, ReactiveFormsModule],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(AddPostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  it('should create the form with the required controls', () => {
    expect(component.postForm).toBeTruthy();
    expect(component.postForm.controls['title'].value).toBe('');
    expect(component.postForm.controls['content'].value).toBe('');
    expect(component.postForm.controls['tags'].value).toBe('');
    expect(component.postForm.controls['isDraft'].value).toBe(false);
  });

  it("should add a tag to the tags array", () => {
    const event = new KeyboardEvent("keydown", { key: " " });
    const input = document.createElement("input");
    input.value = "Tag";
    const spy = spyOnProperty(event, "target").and.returnValue(input);

    component.addTag(event);

    expect(component.tags).toContain("Tag");
  });

  it("should remove a tag from the tags array", () => {
    component.tags = ["Tag1", "Tag2"];

    component.removeTag("Tag1");

    expect(component.tags).not.toContain("Tag1");
  });

  it("should call addPost on form submit an navigate on success", () => {
    const mockPostRequest = {
      title: "Title",
      content: "Content",
      tags: "Tag1, Tag2",
      author: "Laura Willems",
      isDraft: false
    };

    const mockPost: Post = {
      id: 1,
      title: "Title",
      content: "Content",
      tags: ["Tag1", "Tag2"],
      author: "Laura Willems",
      draft: false,
      publishedDate: new Date(),
      status: "published"
    };

    component.postForm.setValue({
      title: mockPostRequest.title,
      content: mockPostRequest.content,
      tags: mockPostRequest.tags,
      isDraft: mockPostRequest.isDraft
    });
    postServiceMock.addPost.and.returnValue(of(mockPost));

    component.onSubmit();

    expect(postServiceMock.addPost).toHaveBeenCalledWith(jasmine.any(PostRequest));
    expect(component.postForm.pristine).toBeTrue();
    expect(routerMock.navigate).toHaveBeenCalledWith(["/posts"]);
  })
});
