import {AuthService} from "./auth.service";

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    service = new AuthService();
    localStorage.clear();
  });


  it('should set and get user role', () => {
    service.setUserRole('editor');
    expect(service.getUserRole()).toBe('editor');
  });

  it('should set and get user name', () => {
    service.setUserName('John', 'Doe');
    expect(service.getUserName()).toBe('John Doe');
  });

  it('should login and set user details', () => {
    service.login('John', 'Doe', 'editor');
    expect(service.getUserName()).toBe('John Doe');
    expect(service.getUserRole()).toBe('editor');
  });

  it('should logout and clear user details', () => {
    service.login('John', 'Doe', 'editor');
    service.logout();
    expect(service.getUserName()).toBe('');
    expect(service.getUserRole()).toBe('');
  });

  it('should get headers with user details', () => {
    service.login('John', 'Doe', 'editor');
    const headers = service.getHeaders();
    expect(headers.get('user-role')).toBe('editor');
    expect(headers.get('user-name')).toBe('John Doe');
  });
});
