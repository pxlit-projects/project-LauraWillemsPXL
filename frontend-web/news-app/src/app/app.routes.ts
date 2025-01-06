import { Routes } from '@angular/router';
import {LoginComponent} from "./core/login/login.component";
import {PostOverviewComponent} from "./core/post-overview/post-overview.component";
import {AddPostFormComponent} from "./core/add-post-form/add-post-form.component";
import {DraftOverviewComponent} from "./core/draft-overview/draft-overview.component";
import {DraftDetailsComponent} from "./core/draft-details/draft-details.component";
import {PostDetailsComponent} from "./core/post-details/post-details.component";
import {PostRejectedDetailsComponent} from "./core/post-rejected-details/post-rejected-details.component";
import {NotificationsComponent} from "./core/notifications/notifications.component";

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
    path: 'posts/:id/details',
    component: PostDetailsComponent
  },
  {
    path: 'posts/:id/rejected/edit',
    component: PostRejectedDetailsComponent
  },
  {
    path: 'posts/addPost',
    component: AddPostFormComponent
  },
  {
    path: "drafts",
    component: DraftOverviewComponent
  },
  {
    path: "drafts/:id",
    component: DraftDetailsComponent
  },
  {
    path: "notifications",
    component: NotificationsComponent
  }
];
