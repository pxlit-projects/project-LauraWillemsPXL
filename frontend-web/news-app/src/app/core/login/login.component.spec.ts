import { ComponentFixture, TestBed } from "@angular/core/testing";
import {LoginComponent} from "./login.component";
import {Router} from "@angular/router";
import {AuthService} from "../../shared/services/auth.service";

describe("LoginComponent", () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: jasmine.SpyObj<AuthService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(() => {
    authServiceMock = jasmine.createSpyObj("AuthService", ["login"]);
    routerMock = jasmine.createSpyObj("Router", ["navigate"]);

    TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    });

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  it("should call the login method of the authService and navigate to the posts page", () => {
    component.firstName = "John";
    component.lastName = "Doe";
    component.userRole = "editor";

    component.login();

    expect(authServiceMock.login).toHaveBeenCalledWith("John", "Doe", "editor");
    expect(routerMock.navigate).toHaveBeenCalledWith(["/posts"]);
  });
});
