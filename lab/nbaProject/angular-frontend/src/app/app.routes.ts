import { Routes } from '@angular/router';
import { FranchiseListComponent } from './components/franchise-list/franchise-list.component';
import { FranchiseAddComponent } from './components/franchise-add/franchise-add.component';
import { FranchiseEditComponent } from './components/franchise-edit/franchise-edit.component';
import { FranchiseDetailsComponent } from './components/franchise-details/franchise-details.component';
import { PlayerAddComponent } from './components/player-add/player-add.component';
import { PlayerEditComponent } from './components/player-edit/player-edit.component';
import { PlayerDetailsComponent } from './components/player-details/player-details.component';

export const routes: Routes = [
  { path: '', redirectTo: '/franchises', pathMatch: 'full' },
  { path: 'franchises', component: FranchiseListComponent },
  { path: 'franchises/add', component: FranchiseAddComponent },
  { path: 'franchises/edit/:id', component: FranchiseEditComponent },
  { path: 'franchises/:id', component: FranchiseDetailsComponent },
  { path: 'franchises/:franchiseId/players/add', component: PlayerAddComponent },
  { path: 'franchises/:franchiseId/players/edit/:playerId', component: PlayerEditComponent },
  { path: 'franchises/:franchiseId/players/:playerId', component: PlayerDetailsComponent }
];