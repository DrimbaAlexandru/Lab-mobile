import { NgModule } from '@angular/core';
import { LandingComponent } from './landing.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [ CommonModule, RouterModule, FormsModule ],
  declarations: [LandingComponent]
})
export class LandingModule { }
