import { ComponentFixture, TestBed } from "@angular/core/testing";
import {NavbarComponent} from "./navbar.component";
import {AuthService} from "../../shared/services/auth.service";
import {Router} from "@angular/router";
import {of} from "rxjs";

describe("NavbarComponent", () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let authServiceMock: jasmine.SpyObj<AuthService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(() => {
    authServiceMock = jasmine.createSpyObj("AuthService", ["logout"]);
    routerMock = jasmine.createSpyObj("Router", ["navigate"]);

    TestBed.configureTestingModule({
      imports: [NavbarComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    });

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  it("should call logout method of authService and navigate to home on logout", () => {
    component.logout();
    expect(authServiceMock.logout).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(["/"]);
  });

  it("should navigate to posts page on onPostsClick", () => {
    component.onPostsClick();
    expect(routerMock.navigate).toHaveBeenCalledWith(["/posts"]);
  });

  it("should navigate to drafts page on onDraftsClick", () => {
    component.onDraftsClick();
    expect(routerMock.navigate).toHaveBeenCalledWith(["/drafts"]);
  });

  it("should navigate to notifications page on onNotificationsClick", () => {
    component.onNotificationsClick();
    expect(routerMock.navigate).toHaveBeenCalledWith(["/notifications"]);
  });
});
