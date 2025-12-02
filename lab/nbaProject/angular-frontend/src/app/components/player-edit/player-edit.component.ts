import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PlayerService } from '../../services/player.service';
import { FranchiseService } from '../../services/franchise.service';

@Component({
  selector: 'app-player-edit',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './player-edit.component.html',
  styleUrl: './player-edit.component.css'
})
export class PlayerEditComponent implements OnInit {
  playerForm: FormGroup;
  loading = false;
  loadingData = true;
  error: string | null = null;
  franchiseId: string = '';
  playerId: string = '';
  franchiseName: string = '';
  positions = ['GUARD', 'FORWARD', 'CENTER'];

  constructor(
    private fb: FormBuilder,
    private playerService: PlayerService,
    private franchiseService: FranchiseService,
    private router: Router,
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
    this.playerId = this.route.snapshot.paramMap.get('playerId') || '';
    this.loadFranchiseName();
    this.loadPlayer();
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

  loadPlayer(): void {
    this.playerService.getPlayer(this.playerId).subscribe({
      next: (player) => {
        this.playerForm.patchValue({
          firstName: player.firstName,
          lastName: player.lastName,
          age: player.age,
          position: player.position
        });
        this.loadingData = false;
      },
      error: (err) => {
        this.error = 'Failed to load player';
        this.loadingData = false;
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
      this.playerService.updatePlayer(this.playerId, playerData).subscribe({
        next: () => {
          this.router.navigate(['/franchises', this.franchiseId]);
        },
        error: (err) => {
          this.error = 'Failed to update player';
          this.loading = false;
          console.error(err);
        }
      });
    }
  }
}
