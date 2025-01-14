import { ComponentFixture, TestBed } from "@angular/core/testing";
import {NotificationsComponent} from "./notifications.component";
import {PostService} from "../../shared/services/post.service";
import {of} from "rxjs";

describe("NotificationsComponent", () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;

  beforeEach(() => {
    postServiceMock = jasmine.createSpyObj("PostService", ["getNotificationsOfAuthor"]);

    TestBed.configureTestingModule({
      imports: [NotificationsComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock }
      ]
    });

    fixture = TestBed.createComponent(NotificationsComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  it("should retrieve all the notifications on initialization", () => {
    const mockNotifications = ["Notification 1", "Notification 2", "Notification 3"];
    postServiceMock.getNotificationsOfAuthor.and.returnValue(of(mockNotifications));

    component.ngOnInit();

    expect(postServiceMock.getNotificationsOfAuthor).toHaveBeenCalled();
    expect(component.notifications).toEqual(mockNotifications.reverse());
  });
});
