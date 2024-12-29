import {Component, inject} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../shared/services/post.service";
import {PostRequest} from "../../shared/model/postRequest.model";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
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

  postForm: FormGroup = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required],
    isDraft: [false],
  });

  onSubmit(): void {
    let title = this.postForm.get('title')?.value;
    let content = this.postForm.get('content')?.value;
    let isDraft = this.postForm.get('isDraft')?.value;

    console.log('is draft: ', isDraft);

    this.postService.addPost(new PostRequest(title, content, this.authService.getUserName(), isDraft)).subscribe({
      next: () => {
        this.router.navigate(['/posts']);
      },
    });
  }
}
