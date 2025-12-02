export interface FranchiseListDTO {
    id: string;
    name: string;
}

export interface FranchiseReadDTO {
    id: string;
    name: string;
    city: string;
    conference: string;
    currentPosition: number;
    titles: number;
}

export interface FranchiseCreateUpdateDTO {
    name: string;
    city?: string;
    confrence?: string;
    currentPosition?: number;
    titles?: number;
}