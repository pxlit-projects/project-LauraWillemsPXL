import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userRole: string = "";
  private userName: string = "";

  setUserRole(role: string): void {
    this.userRole = role;
  }

  getUserRole(): string {
    return this.userRole;
  }

  setUserName(firstName: string, lastName: string) {
    this.userName = `${firstName} ${lastName}`;
  }

  getUserName(): string {
    return this.userName;
  }

  logout() {
    this.userRole = "";
    this.userName = "";
  }

  getHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.set('user-role', this.getUserRole());
    headers = headers.set('user-name', this.getUserName());
    return headers;
  }
}
