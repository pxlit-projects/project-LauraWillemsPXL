import {Component, inject} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../shared/services/post.service";
import {PostRequest} from "../../shared/model/postRequest.model";
import {Router} from "@angular/router";
import {AuthService} from "../../shared/services/auth.service";

@Component({
  selector: 'app-add-post-form',
  standalone: true,
  imports: [
    MatCardModule,
    NavbarComponent,
    ReactiveFormsModule
  ],
  templateUrl: './add-post-form.component.html',
  styleUrl: './add-post-form.component.css'
})
export class AddPostFormComponent {
  fb: FormBuilder = inject(FormBuilder);
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);
  tags: string[] = [];

  postForm: FormGroup = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required],
    tags: ['', Validators.required],
    isDraft: [false],
  });

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

  onSubmit(): void {
    let title = this.postForm.get('title')?.value;
    let content = this.postForm.get('content')?.value;
    let isDraft = this.postForm.get('isDraft')?.value;

    this.postService.addPost(new PostRequest(title, content, this.tags, this.authService.getUserName(), isDraft)).subscribe({
      next: () => {
        this.router.navigate(['/posts']);
      },
    });
  }
}
