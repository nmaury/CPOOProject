import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Tile} from '../../models/tile.model';

@Injectable({
  providedIn: 'root'
})
export class TileService {
  constructor(private http:HttpClient) {  }
  getTiles(gameid:number): Observable<Array<Object>> {
    return this.http.get<Array<Object>>('city/game/'+gameid+'/map/tile');
  }
}
