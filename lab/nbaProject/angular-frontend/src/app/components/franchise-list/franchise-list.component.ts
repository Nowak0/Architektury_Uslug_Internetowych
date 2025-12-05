import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FranchiseService } from '../../services/franchise.service';
import { FranchiseListDTO } from '../../models/franchise.model';

@Component({
    selector: 'app-franchise-list',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './franchise-list.component.html',
    styleUrl: './franchise-list.component.css'
})

export class FranchiseListComponent implements OnInit {
    franchises: FranchiseListDTO[] = [];
    loading = false;
    error: string | null = null;

    constructor (private franchiseService: FranchiseService) {}

    ngOnInit(): void {
        this.loadFranchise();
    }

    loadFranchise(): void {
        this.loading = true;
        this.error = null;
        this.franchiseService.getAllFranchises().subscribe({
            next: (data) => {
                this.franchises = data;
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Failed to load franchises';
                this.loading = false;
                console.error(err);
            }
        });
    }

    deleteFranchise(id: string, name: string): void {
        if(confirm(`Are you sure you want to delete "${name}"`)) {
            this.franchiseService.deleteFranchise(id).subscribe({
                next: () => {
                    this.loadFranchise();
                },
                error: (err) => {
                    this.error = 'Failed to delete franchise';
                    console.error(err);
                }
            });
        }
    }
}