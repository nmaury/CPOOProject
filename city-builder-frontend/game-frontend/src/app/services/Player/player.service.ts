import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Game } from 'src/app/models/game.model';
import { Top5Score } from 'src/app/models/top5score.model';
import {Player} from '../../models/player.model';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  //private servicePlayer: Player;
 
  constructor(private http: HttpClient) { 
  }
  getPlayers(){
    return this.http.get<Array<Player>>('city/player');
  }
  getPlayer(id:number) {
    return this.http.get<Player>('city/player/'+id);
  }
  modifyPlayer(player:Player){   
    return new Promise((resolve, reject) => {
        this.http
          .put<Player>(`city/player/${player.id}/${player.user_name}`,{})
          .subscribe(
            (response) => {
              console.log('Changement Effectué !');
              resolve(response);
            },
            (error) => {
              console.log('Erreur ! : ' + error);
              reject(error);
            }
          );
    });
  }
  addPlayer(user_name:String){
   return new Promise((resolve, reject) => {
      this.http
        .post(`city/player/${user_name}`,{})
        .subscribe(
          (response) => {
            console.log('Enregistrement terminé !');
            resolve(response);
          },
          (error) => {
            console.log('Erreur ! : ' + error);
            reject(error);
          }
        );
   });
  }

  addGame(id:number,game : Game){
    return new Promise((resolve, reject) => {
      this.http.post(`city/player/${id}/${game.id}/${game.game_name}/${game.map.name}`,{}).subscribe(
        (response) => {
          resolve(response);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
  
  getTop5Score(mapname:string){
    return new Promise(resolve =>{
      this.getPlayers()
      .subscribe(players => {
        var tabscore :Top5Score[]=[];
        players.forEach(p =>{
          //console.log(p.games);
            const map_games = p.games.filter(elt => elt.map.name == mapname);
            //console.log(map_games);
            map_games.forEach(g=> {
              const topScore = new Top5Score();
              topScore.player = p.user_name;
              topScore.score = g.map.score;
            tabscore.push(topScore);
            });
            });
            console.log(tabscore);
            resolve(tabscore);
      });
    });
  }

}
