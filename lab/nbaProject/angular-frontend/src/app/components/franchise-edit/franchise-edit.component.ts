import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FranchiseService } from '../../services/franchise.service';

@Component({
    selector: 'app-franchise-edit',
    standalone: true,
    imports: [CommonModule, RouterModule, ReactiveFormsModule],
    templateUrl: './franchise-edit.component.html',
    styleUrl: './franchise-edit.component.css'
})

export class FranchiseEditComponent implements OnInit {
    franchiseForm: FormGroup;
    loading = false;
    loadingData = true;
    error: string | null = null;
    franchiseId: string = '';

    constructor(
        private fb: FormBuilder,
        private franchiseService: FranchiseService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.franchiseForm = this.fb.group({
            name: ['', Validators.required],
            city: [''],
            conference: [''],
            currentPosition: [null],
            titles: [null]
        });
    }

    ngOnInit(): void {
        this.franchiseId = this.route.snapshot.paramMap.get('id') || '';
        this.loadFranchise();
    }

    loadFranchise(): void {
        this.franchiseService.getFranchise(this.franchiseId).subscribe({
            next: (franchise) => {
                this.franchiseForm.patchValue({
                    name: franchise.name,
                    city: franchise.city,
                    conference: franchise.conference,
                    currentPosition: franchise.currentPosition,
                    titles: franchise.titles
                });
                this.loadingData = false;
            }
        })
    }

    onSubmit(): void {
        if (this.franchiseForm.valid) {
            this.loading = true;
            this.error = null;
            this.franchiseService.updateFranchise(this.franchiseId, this.franchiseForm.value).subscribe({
                next: () => {
                    this.router.navigate(['/franchises']);
                },
                error: (err) => {
                    this.error = 'Failed to update franchise';
                    this.loading = false;
                    console.error(err);
                }
            });
        }
    }
}