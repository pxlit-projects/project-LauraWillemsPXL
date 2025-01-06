import {Component, inject, OnInit} from '@angular/core';
import {PostService} from "../../shared/services/post.service";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [
    NavbarComponent
  ],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements OnInit {
  postService: PostService = inject(PostService);
  notifications: string[] = [];

  ngOnInit(): void {
    this.postService.getNotificationsOfAuthor().subscribe(response => {
      this.notifications = response;
    });
  }

}
