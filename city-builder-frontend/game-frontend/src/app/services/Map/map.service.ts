import { Injectable } from '@angular/core';
import {Map} from '../../models/map.model';
import { Tile } from 'src/app/models/tile.model';
import { TileService } from '../Tile/tile.service';
import { PieceService } from '../Piece/piece.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  public currentMap! : Map;
  constructor(private http:HttpClient) { }
  
  getmap(gameid:number) {
    return this.http.get<Map>('city/game/'+gameid+'/map');
  }
  setTile(gameid:number,place:number,tile:Tile,score:number,score_limit:number){
    const re=/\w+\/(\w+).svg/;
    const type = tile.type.replace(re,'$1');
    return new Promise((resolve, reject) => {
      this.http.put('city/game/'+gameid+'/map/tile/'+place+'/'+type+'/'+score+'/'+score_limit,{}).subscribe(
        (response) => {
          resolve(response);
        },
        (error) => {
          reject(error);
        }
      );
    }); 
  }
  removeTile(gameid:number,place:number, score: number, score_limit : number){
    console.log("tile removed!")
    return new Promise((resolve, reject) => {
      this.http.delete('city/game/'+gameid+'/map/tile/'+place+'/'+score+'/'+score_limit).subscribe(
        (response) => {
          resolve(response);
        },
        (error) => {
          reject(error);
        }
      );
    }); 
  }
  
}
