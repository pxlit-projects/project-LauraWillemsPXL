import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userRole: string = "";
  private userName: string = "";

  constructor() {
    this.loadUserFromLocalStorage();
  }

  setUserRole(role: string): void {
    this.userRole = role;
    this.saveUserToLocalStorage();
  }

  getUserRole(): string {
    return this.userRole;
  }

  setUserName(firstName: string, lastName: string) {
    this.userName = `${firstName} ${lastName}`;
    this.saveUserToLocalStorage();
  }

  getUserName(): string {
    return this.userName;
  }

  login(firstName: string, lastName: string, role: string) {
    this.setUserName(firstName, lastName);
    this.setUserRole(role);
  }

  logout() {
    this.userRole = "";
    this.userName = "";

    localStorage.removeItem("userRole");
    localStorage.removeItem("userName");
  }

  getHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.set('user-role', this.getUserRole());
    headers = headers.set('user-name', this.getUserName());
    return headers;
  }

  private saveUserToLocalStorage(): void {
    localStorage.setItem('userRole', this.userRole);
    localStorage.setItem('userName', this.userName);
  }

  private loadUserFromLocalStorage() {
    const storedUserRole = localStorage.getItem('userRole');
    const storedUserName = localStorage.getItem('userName');
    if (storedUserRole) {
      this.userRole = storedUserRole;
    }
    if (storedUserName) {
      this.userName = storedUserName;
    }
  }
}
