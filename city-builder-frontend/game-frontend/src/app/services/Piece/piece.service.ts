import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Piece} from '../../models/piece.model';

@Injectable({
  providedIn: 'root'
})
export class PieceService {
  constructor(private http:HttpClient) { }
  getPieces(gameid:number) {
    return this.http.get<Array<Piece>>('city/game/'+gameid+'/map/piece');
  }
}
