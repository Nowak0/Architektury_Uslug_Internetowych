import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PlayerService } from '../../services/player.service';
import { FranchiseService } from '../../services/franchise.service';
import { PlayerReadDTO } from '../../models/player.model';
import { FranchiseListDTO } from '../../models/franchise.model';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.css'
})
export class PlayerDetailsComponent implements OnInit {
  player: PlayerReadDTO | null = null;
  franchiseName: string = '';
  loading = false;
  error: string | null = null;
  franchiseId: string = '';
  playerId: string = '';
  
  // Transfer functionality
  franchises: FranchiseListDTO[] = [];
  selectedFranchiseId: string = '';
  transferLoading = false;
  transferSuccess: string | null = null;
  transferError: string | null = null;

  constructor(
    private playerService: PlayerService,
    private franchiseService: FranchiseService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.franchiseId = this.route.snapshot.paramMap.get('franchiseId') || '';
    this.playerId = this.route.snapshot.paramMap.get('playerId') || '';
    this.loadPlayer();
    this.loadFranchiseName();
    this.loadFranchises();
  }

  loadPlayer(): void {
    this.loading = true;
    this.playerService.getPlayer(this.playerId).subscribe({
      next: (data) => {
        this.player = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load player';
        this.loading = false;
        console.error(err);
      }
    });
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

  loadFranchises(): void {
    this.franchiseService.getAllFranchises().subscribe({
      next: (data) => {
        this.franchises = data.filter(f => f.id !== this.franchiseId);
      },
      error: (err) => {
        console.error('Failed to load franchises', err);
      }
    });
  }

  transferPlayer(): void {
    if (!this.selectedFranchiseId || this.selectedFranchiseId === this.franchiseId) {
      this.transferError = 'Please select a different franchise';
      return;
    }

    this.transferLoading = true;
    this.transferSuccess = null;
    this.transferError = null;

    const targetFranchise = this.franchises.find(f => f.id === this.selectedFranchiseId);
    const playerFullName = this.player ? `${this.player.firstName} ${this.player.lastName}` : 'Player';

    this.playerService.transferPlayer(this.playerId, this.selectedFranchiseId).subscribe({
      next: () => {
        this.transferLoading = false;
        this.transferSuccess = `${playerFullName} has been transferred to ${targetFranchise?.name || 'new franchise'}`;
        
        // Update local state
        this.franchiseId = this.selectedFranchiseId;
        if (this.player) {
          this.player.franchiseId = this.selectedFranchiseId;
        }
        this.franchiseName = targetFranchise?.name || '';
        this.selectedFranchiseId = '';
        
        // Reload franchises list (excluding new current franchise)
        this.loadFranchises();
      },
      error: (err) => {
        this.transferLoading = false;
        if (err.status === 404) {
          this.transferError = 'Nie znaleziono gracza lub franchise';
        } else {
          this.transferError = 'Nie udało się przenieść gracza. Spróbuj ponownie.';
        }
        console.error(err);
      }
    });
  }
}
