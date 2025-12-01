import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FranchiseListDTO, FranchiseReadDTO, FranchiseCreateUpdateDTO } from '../models/franchise.model';

@Injectable({
  providedIn: 'root'
})
export class FranchiseService {
  private apiUrl = '/api/franchises';

  constructor(private http: HttpClient) {}

  getAllFranchises(): Observable<FranchiseListDTO[]> {
    return this.http.get<FranchiseListDTO[]>(this.apiUrl);
  }

  getFranchise(id: string): Observable<FranchiseReadDTO> {
    return this.http.get<FranchiseReadDTO>(`${this.apiUrl}/${id}`);
  }

  createFranchise(franchise: FranchiseCreateUpdateDTO): Observable<FranchiseReadDTO> {
    return this.http.post<FranchiseReadDTO>(this.apiUrl, franchise);
  }

  updateFranchise(id: string, franchise: FranchiseCreateUpdateDTO): Observable<FranchiseReadDTO> {
    return this.http.put<FranchiseReadDTO>(`${this.apiUrl}/${id}`, franchise);
  }

  deleteFranchise(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
