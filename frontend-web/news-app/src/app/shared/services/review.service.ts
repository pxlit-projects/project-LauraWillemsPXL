import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {environment} from "../../../environments/environment.development";
import {RejectPostRequest} from "../model/rejectPostRequest.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  httpClient: HttpClient = inject(HttpClient);
  authService: AuthService = inject(AuthService);

  approvePost(postId: number) {
    const headers = this.authService.getHeaders();
    return this.httpClient.post<void>(`${environment.api}/review/api/review/approve/${postId}`, null, { headers });
  }

  rejectPost(postId: number, rejectPostRequest: RejectPostRequest) {
    const headers = this.authService.getHeaders();
    return this.httpClient.post<void>(`${environment.api}/review/api/review/reject/${postId}`, rejectPostRequest, { headers });
  }
}
