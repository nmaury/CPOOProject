import { HttpClient } from '@angular/common/http';
import { NULL_EXPR } from '@angular/compiler/src/output/output_ast';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Player } from 'src/app/models/player.model';
import { Tile } from 'src/app/models/tile.model';
import {Game} from '../../models/game.model';
import { MapService } from '../Map/map.service';
@Injectable({
  providedIn: 'root'
})
export class GameService {
  public currentGame: Game;
  constructor(private http: HttpClient,private mapservice:MapService) { 
  }
  getgames(): Observable<Array<Game>> {
    return this.http.get<Array<Game>>('city/game');
  }
  getgame(id:number){
    return this.http.get<Game>('city/game/'+id);
  }
  /*newGame(game:Game,playerid:number){
    return new Promise((resolve, reject) => {
      this.http.post(`city/game/${game.id}/${game.game_name}/${playerid}/${game.map.name}`,game).subscribe(
        (response) => {
          resolve(response);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }*/
  setTile(place:number,tile:Tile, score: number, score_limit: number){
    //console.log("new tile");
    //console.log(this.currentGame);
    this.mapservice.setTile(this.currentGame.id,place,tile,score,score_limit);
  }
  removeTile(place:number,score: number, score_limit: number){
    console.log('undo');
    this.mapservice.removeTile(this.currentGame.id,place, score, score_limit);
  }
  updateScore(gameid:number,score:number){
    this.http
    .put<Game>(`city/game/${gameid}/${score}`,{})
    .subscribe(
      () => {
        console.log('Score Sauvegardé !');
      },
      (error) => {
        console.log('Erreur ! : ' + error);
      }
    );
  }
  endGame(mapname:string,scorefinal:number,playername:string){
    this.http
    .post('city/end/'+this.currentGame.id+'/'+mapname+'/'+scorefinal+'/'+playername,{})
    .subscribe(
      () => 
    {
      console.log('Partie Sauvegardée !');
    },
    (error) => {
      console.log('Erreur ! : ' + error);
    }
  );
  }


}
