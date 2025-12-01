import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { PlayerService } from '../../services/player.service';
import { FranchiseService } from '../../services/franchise.service';
import { PlayerReadDTO } from '../../models/player.model';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
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

  constructor(
    private playerService: PlayerService,
    private franchiseService: FranchiseService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.franchiseId = this.route.snapshot.paramMap.get('franchiseId') || '';
    this.playerId = this.route.snapshot.paramMap.get('playerId') || '';
    this.loadPlayer();
    this.loadFranchiseName();
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
}
