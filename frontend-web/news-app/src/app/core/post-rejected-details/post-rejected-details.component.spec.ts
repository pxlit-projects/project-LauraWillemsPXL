import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostRejectedDetailsComponent } from './post-rejected-details.component';

describe('PostRejectedDetailsComponent', () => {
  let component: PostRejectedDetailsComponent;
  let fixture: ComponentFixture<PostRejectedDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostRejectedDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostRejectedDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
