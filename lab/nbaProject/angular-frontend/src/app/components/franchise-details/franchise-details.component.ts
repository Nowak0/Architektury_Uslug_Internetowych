import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { FranchiseService } from '../../services/franchise.service';
import { PlayerService } from '../../services/player.service';
import { FranchiseReadDTO } from '../../models/franchise.model';
import { PlayerListDTO } from '../../models/player.model';

@Component({
  selector: 'app-franchise-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './franchise-details.component.html',
  styleUrl: './franchise-details.component.css'
})
export class FranchiseDetailsComponent implements OnInit {
  franchise: FranchiseReadDTO | null = null;
  players: PlayerListDTO[] = [];
  loading = false;
  loadingPlayers = false;
  error: string | null = null;
  franchiseId: string = '';

  constructor(
    private franchiseService: FranchiseService,
    private playerService: PlayerService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.franchiseId = this.route.snapshot.paramMap.get('id') || '';
    this.loadFranchise();
    this.loadPlayers();
  }

  loadFranchise(): void {
    this.loading = true;
    this.franchiseService.getFranchise(this.franchiseId).subscribe({
      next: (data) => {
        this.franchise = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load franchise';
        this.loading = false;
        console.error(err);
      }
    });
  }

  loadPlayers(): void {
    this.loadingPlayers = true;
    this.playerService.getPlayersByFranchise(this.franchiseId).subscribe({
      next: (data) => {
        this.players = data || [];
        this.loadingPlayers = false;
      },
      error: (err) => {
        if (err.status !== 204) {
          console.error(err);
        }
        this.players = [];
        this.loadingPlayers = false;
      }
    });
  }

  deletePlayer(id: string, name: string): void {
    if (confirm(`Are you sure you want to delete "${name}"?`)) {
      this.playerService.deletePlayer(id).subscribe({
        next: () => {
          this.loadPlayers();
        },
        error: (err) => {
          this.error = 'Failed to delete player';
          console.error(err);
        }
      });
    }
  }
}
