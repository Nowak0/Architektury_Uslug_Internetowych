import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PlayerService } from '../../services/player.service';
import { FranchiseService } from '../../services/franchise.service';

@Component({
    selector: 'app-player-add',
    standalone: true,
    imports: [CommonModule, RouterModule, ReactiveFormsModule],
    templateUrl: './player-add.component.html',
    styleUrl: './player-add.component.css'
})

export class PlayerAddComponent implements OnInit {
    playerForm: FormGroup;
    loading = false;
    error: string | null = null;
    franchiseId: string = '';
    franchiseName: string = '';
    positions = ['GUARD', 'FORWARD', 'CENTER'];

    constructor(
        private fb: FormBuilder,
        private playerService: PlayerService,
        private franchiseService: FranchiseService,
        private router:Router,
        private route: ActivatedRoute
    ) {
        this.playerForm = this.fb.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            age: [null, [Validators.required, Validators.min(18), Validators.max(50)]],
            position: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.franchiseId = this.route.snapshot.paramMap.get('franchiseId') || '';
        this.loadFranchiseName();
    }

    loadFranchiseName(): void {
        this.franchiseService.getFranchise(this.franchiseId).subscribe({
            next: (franchise) => {
                this.franchiseName = franchise.name;
            },
            error: (err) => {
                console.error(err);
            }
        });
    }

    onSubmit(): void {
        if (this.playerForm.valid) {
            this.loading = true;
            this.error = null;
            const formValue = this.playerForm.value;
            const playerData = {
                firstName: formValue.firstName,
                lastName: formValue.lastName,
                age: formValue.age,
                position: formValue.position
            };
            
            this.playerService.createPlayer(this.franchiseId, playerData).subscribe({
                next: () => {
                    this.router.navigate(['/franchises', this.franchiseId]);
                },
                error: (err) => {
                    this.error = 'Failed to create player';
                    this.loading = false;
                    console.error(err);
                }
            });
        }
    }
}