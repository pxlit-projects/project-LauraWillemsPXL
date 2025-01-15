import { CanDeactivateFn } from '@angular/router';
import {AddPostFormComponent} from "./core/add-post-form/add-post-form.component";

export const confirmLeaveGuard: CanDeactivateFn<AddPostFormComponent> = (component, currentRoute, currentState, nextState) => {
  if (component.postForm.valid) {
    return true;
  }
  if (component.postForm.dirty) {
    return window.confirm('You have unsaved changes. Are you sure you want to leave?');
  }
  else {
    return true;
  }
};
