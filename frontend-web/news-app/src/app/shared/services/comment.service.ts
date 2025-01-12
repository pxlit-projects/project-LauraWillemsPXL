import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {CommentRequest} from "../model/CommentRequest.model";
import {Comment} from "../model/comment.model";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  http: HttpClient = inject(HttpClient);
  authService: AuthService = inject(AuthService);

  addComment(commentRequest: CommentRequest) {
    return this.http.post<Comment>(`${environment.api}/comment/api/comment`, commentRequest);
  }

  getAllComments() {
    return this.http.get<Comment[]>(`${environment.api}/comment/api/comment`);
  }

  updateComment(id: number, commentRequest: CommentRequest) {
    const headers = this.authService.getHeaders();
    return this.http.put<Comment>(`${environment.api}/comment/api/comment/update/${id}`, commentRequest, { headers });
  }

  deleteComment(id: number) {
    const headers = this.authService.getHeaders();
    return this.http.delete<void>(`${environment.api}/comment/api/comment/delete/${id}`, { headers });
  }
}
