export type Position = 'GUARD' | 'FORWARD' | 'CENTER';

export interface PlayerListDTO {
  id: string;
  fullName: string;
}

export interface PlayerReadDTO {
  id: string;
  firstName: string;
  lastName: string;
  age: number;
  position: Position;
  number: number;
  franchiseId: string;
}

export interface PlayerCreateUpdateDTO {
  firstName: string;
  lastName: string;
  age?: number;
  position?: Position;
  number?: number;
}
