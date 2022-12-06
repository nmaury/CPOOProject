import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Game } from 'src/app/models/game.model';
import { Map } from 'src/app/models/map.model';
import { Player } from 'src/app/models/player.model';
import { GameService } from 'src/app/services/Game/game.service';
import { PlayerService } from 'src/app/services/Player/player.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  players! : Player[];
  playerid: number;
  player_name : string;
  constructor(private router: Router, private playerservice : PlayerService ,private gameservice: GameService) {
    this.playerid=0;
    this.player_name="";
   
  }

  ngOnInit(): void {
    this.NewPlayer();
    this.playerservice.getPlayers().subscribe(players =>{
      this.players = players;
      });
    
  }
  ToReplay(){
    this.router.navigate(['chooseReplay']);
  }
  /* accès à la carte nommée La Flèche*/
  ToLFMap(){
    this.gameservice.getgames().subscribe(games =>{
       var id = games.length;
       //console.log(id);
       const game = new Game();
       game.game_name = "game"+id;
       game.id = id;
       game.player = this.players[this.playerid].user_name;
       game.map = new Map();
       game.map.name = "LaFleche";
       this.playerservice.addGame(this.playerid,game).then(
       () => {
          this.router.navigate(['game/'+game.map.name+'/'+id+'/'+this.playerid]);
         }
       ).catch(
         (error) => {
           console.log(error.message);
         }
       ); 
      })       
   
  }
   /* accès à la carte nommée Bordeaux*/
  ToBdxMap(){
    this.gameservice.getgames().subscribe(games =>{
       var id = games.length;
       //console.log(id);
       const game = new Game();
       game.game_name = "game"+id;
       game.id = id;
       game.player = this.players[this.playerid].user_name;
       game.map = new Map();
       game.map.name = "Bordeaux";
       this.playerservice.addGame(this.playerid,game).then(
       () => {
          this.router.navigate(['game/'+game.map.name+'/'+id+'/'+this.playerid]);
         }
       ).catch(
         (error) => {
           console.log(error.message);
         }
       ); 
      })       
   
  }
   /* accès à la carte nommée Rennes*/
  ToRnsMap(){
    this.gameservice.getgames().subscribe(games =>{
       var id = games.length;
       //console.log(id);
       const game = new Game();
       game.game_name = "game"+id;
       game.id = id;
       game.player = this.players[this.playerid].user_name;
       game.map = new Map();
       game.map.name = "Rennes";
       this.playerservice.addGame(this.playerid,game).then(
       () => {
          this.router.navigate(['game/'+game.map.name+'/'+id+'/'+this.playerid]);
         }
       ).catch(
         (error) => {
           console.log(error.message);
         }
       ); 
      })       
   
  }
   /* accès à la carte aléatoire*/
  ToRandMap(){
    this.gameservice.getgames().subscribe(games =>{
       var id = games.length;
       //console.log(id);
       const game = new Game();
       game.game_name = "game"+id;
       game.id = id;
       game.player = this.players[this.playerid].user_name;
       game.map = new Map();
       game.map.name = "Random";
       this.playerservice.addGame(this.playerid,game).then(
       () => {
          this.gameservice.getgame(id).subscribe(new_game=>{
          this.router.navigate(['game/'+new_game.map.name+'/'+id+'/'+this.playerid]);
        })
         }
       ).catch(
         (error) => {
           console.log(error.message);
         }
       ); 
      })       
   
  }
  /* fabrication d'un nom aléatoire pour la carte aléatoire*/ 
  randomName(){
    let alphabet = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
    for(let i=0;i<8;i++){
      let random = Math.ceil(Math.random() * 25);
    this.player_name+= alphabet[random];
    }
  }
  
  NewPlayer(){ 
    this.randomName();
    console.log(this.player_name);
    this.playerservice.addPlayer(this.player_name).then(
      id  =>{
        this.playerid = Number(id);
      }
      );
  
  }
}
