import {Component, inject, Input, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Post} from "../../shared/model/post.model";
import {PostService} from "../../shared/services/post.service";
import {PostRequest} from "../../shared/model/postRequest.model";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-draft-details',
  standalone: true,
  imports: [
    NavbarComponent,
    ReactiveFormsModule
  ],
  templateUrl: './draft-details.component.html',
  styleUrl: './draft-details.component.css'
})
export class DraftDetailsComponent implements OnInit {
  draftId!: number;
  draft!: Post;
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  fb: FormBuilder = inject(FormBuilder);
  postForm!: FormGroup;
  tags: string[] = [];

  ngOnInit(): void {
    this.draftId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.postService.getPostById(this.draftId).subscribe(response => {
      this.draft = response;
      this.tags = this.draft.tags;
      this.initializeForm();
    })
  }

  initializeForm(): void {
    this.postForm = this.fb.group({
      title: [this.draft.title, Validators.required],
      content: [this.draft.content, Validators.required],
      tags: [this.draft.tags, Validators.required],
    })
  }

  addTag(event: KeyboardEvent): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.trim();

    if ((event.key === ' ') && value) {
      if (!this.tags.includes(value)) {
        this.tags.push(value);
        this.postForm.get('tags')?.setValue(this.tags.join(','));
        input.value = '';
      }
    }
  }

  removeTag(tag: string): void {
    this.tags = this.tags.filter((t) => t !== tag);
    this.postForm.get('tags')?.setValue(this.tags.join(','));
  }

  updateDraft(): void {
    let title = this.postForm.get('title')?.value;
    let content = this.postForm.get('content')?.value;
    let tags = this.postForm.get('tags')?.value;

    this.postService.updatePost(this.draftId, new PostRequest(title, content, tags, this.authService.getUserName(), true)).subscribe(response => {
      this.router.navigate(['/drafts']);
    });
  }

  onSubmit() {
    let title = this.postForm.get('title')?.value;
    let content = this.postForm.get('content')?.value;
    let tags = this.postForm.get('tags')?.value;

    this.postService.addPost(new PostRequest(title, content, tags, this.authService.getUserName(), false)).subscribe({
      next: () => {
        this.postService.deleteDraft(this.draftId).subscribe();
        this.router.navigate(['/posts']);
      },
    });
  }
}
