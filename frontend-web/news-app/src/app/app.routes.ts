import { Routes } from '@angular/router';
import {LoginComponent} from "./core/login/login.component";
import {PostOverviewComponent} from "./core/post-overview/post-overview.component";
import {AddPostFormComponent} from "./core/add-post-form/add-post-form.component";

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'posts',
    component: PostOverviewComponent
  },
  {
    path: 'posts/addPost',
    component: AddPostFormComponent
  }
];
