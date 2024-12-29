import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {Post} from "../../shared/model/post.model";
import {NavbarComponent} from "../navbar/navbar.component";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {PostCardComponent} from "../post-card/post-card.component";

@Component({
  selector: 'app-draft-overview',
  standalone: true,
  imports: [
    NavbarComponent,
    MatCardModule,
    MatIconModule,
    PostCardComponent
  ],
  templateUrl: './draft-overview.component.html',
  styleUrl: './draft-overview.component.css'
})
export class DraftOverviewComponent implements OnInit {
  router: Router = inject(Router);
  postService: PostService = inject(PostService);
  drafts: Post[] = [];

  ngOnInit(): void {
    this.postService.getAllDrafts().subscribe(response => {
      this.drafts = response;
      for (let draft of this.drafts) {
        console.log(draft);
      }
    });
  }
}
