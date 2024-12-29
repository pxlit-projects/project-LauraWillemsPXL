import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Post} from "../model/post.model";
import {environment} from "../../../environment/environment.development";
import {PostRequest} from "../model/postRequest.model";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  http: HttpClient = inject(HttpClient);
  authService: AuthService = inject(AuthService);


  addPost(postRequest: PostRequest) {
    const headers = this.authService.getHeaders();
    console.log(postRequest);
    return this.http.post<Post>(`${environment.api}/post/api/post`, postRequest, { headers });
  }

  getAllPosts() {
    const headers = this.authService.getHeaders();
    return this.http.get<Post[]>(`${environment.api}/post/api/post`, { headers });
  }

  getAllDrafts() {
    const headers = this.authService.getHeaders();
    return this.http.get<Post[]>(`${environment.api}/post/api/post/drafts`, { headers });
  }
}
