import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FranchiseService } from '../../services/franchise.service';

@Component({
  selector: 'app-franchise-add',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './franchise-add.component.html',
  styleUrl: './franchise-add.component.css'
})
export class FranchiseAddComponent {
  franchiseForm: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private franchiseService: FranchiseService,
    private router: Router
  ) {
    this.franchiseForm = this.fb.group({
      name: ['', Validators.required],
      city: [''],
      conference: [''],
      currentPosition: [null],
      titles: [null]
    });
  }

  onSubmit(): void {
    if (this.franchiseForm.valid) {
      this.loading = true;
      this.error = null;
      this.franchiseService.createFranchise(this.franchiseForm.value).subscribe({
        next: () => {
          this.router.navigate(['/franchises']);
        },
        error: (err) => {
          this.error = 'Failed to create franchise';
          this.loading = false;
          console.error(err);
        }
      });
    }
  }
}
