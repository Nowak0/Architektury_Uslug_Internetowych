import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlayerListDTO, PlayerReadDTO, PlayerCreateUpdateDTO } from '../models/player.model';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private apiUrl = '/api/players';

  constructor(private http: HttpClient) {}

  getAllPlayers(): Observable<PlayerListDTO[]> {
    return this.http.get<PlayerListDTO[]>(this.apiUrl);
  }

  getPlayer(id: string): Observable<PlayerReadDTO> {
    return this.http.get<PlayerReadDTO>(`${this.apiUrl}/${id}`);
  }

  getPlayersByFranchise(franchiseId: string): Observable<PlayerListDTO[]> {
    return this.http.get<PlayerListDTO[]>(`${this.apiUrl}/franchise/${franchiseId}/players`);
  }

  createPlayer(franchiseId: string, player: PlayerCreateUpdateDTO): Observable<PlayerReadDTO> {
    return this.http.post<PlayerReadDTO>(`${this.apiUrl}/${franchiseId}`, player);
  }

  updatePlayer(id: string, player: PlayerCreateUpdateDTO): Observable<PlayerReadDTO> {
    return this.http.put<PlayerReadDTO>(`${this.apiUrl}/${id}`, player);
  }

  deletePlayer(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
