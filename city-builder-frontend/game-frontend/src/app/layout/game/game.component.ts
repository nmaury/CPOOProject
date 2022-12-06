import { Component, ElementRef, Injectable, OnInit, QueryList, ValueProvider, ViewChild, ViewChildren  } from '@angular/core';
import { Tile } from 'src/app/models/tile.model';
import { Piece } from 'src/app/models/piece.model';
import { GameService } from 'src/app/services/Game/game.service';
import { MapService } from 'src/app/services/Map/map.service';
import { PlayerService } from 'src/app/services/Player/player.service';
import { PieceService } from 'src/app/services/Piece/piece.service';
import {MatBadgeModule} from '@angular/material/badge'; 
import { TileService } from 'src/app/services/Tile/tile.service';
import { ClickTile } from 'src/app/command/ClickTile';
import { Game } from 'src/app/models/game.model';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
import {ExitdialogComponent} from '../exitdialog/exitdialog.component';


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
@Injectable({
  providedIn : 'root'
})
export class GameComponent implements OnInit {
  cpt: number=0;
  tiles:Array<Tile>=[];
  pieces: Piece[] =[];
  turn :number =0;
  nb_placements=0;
  score_limit : number =10;
  score : number =0;
  game_name: string | undefined;
  user_name: string | undefined;
  nb_free_circus: number=0;
  nb_free_house: number=0;
  nb_free_windturbine: number=0;
  nb_free_fountain: number=0;
  anc_class:string ="";
  water_list!:Array<Tile>;
  tree_list!:Array<Tile>;
  pieces_posee_list: Array<Tile>;
  current_type!: number;
  currentGame! : Game;
  gameid!:number;
  playerid! : number;
  temp!:number;
  selectedCityBlockIndex: number;
  progressBarScore : number;
  UndoHistory: ClickTile[];
  RedoHistory: ClickTile[];
  mapname!: string;
  nbUndo: number;
  neighbourTiles : [string, number, number, string][] =[["",0,0,""]];
  array: number[]=[];
  

  constructor(private gameservice: GameService, private router: Router, private route: ActivatedRoute, private mapservice: MapService, private playerservice: PlayerService, private pieceservice: PieceService, private tileservice: TileService, private dialog: MatDialog,private exitdialog: MatDialog) { 
    this.pieces_posee_list = [];
    this.selectedCityBlockIndex = -1;
    this.progressBarScore=0
    this.current_type=2;
    this.nbUndo = 0;
    this.current_type=2;
    this.progressBarScore=0;
    this.UndoHistory= [];
    this.RedoHistory= [];
    
  }
public ngOnInit():void{
  this.route.params.subscribe(params => {
    this.gameid = params.gameid;
    this.playerid = params.playerid;
    this.UpdateGame(params.gameid);
    this.tiles=this.UpdateTiles(params.gameid);
    this.UpdatePlayer(params.playerid);
    this.UpdatePieces(params.gameid);
    this.UpdateMap(params.gameid);
    this.anc_class='tile';
    this.current_type = -1;
    this.mapname = params.mapname;
    });
    
  }

setList(name:string,tiles:Object[]){
  let list: Array<Tile> = [];
  tiles.forEach(elt =>{
    var tile = elt as Tile;
      if(tile.type.toLowerCase() == name){
        list.push(tile);
      }
  });
  return list;
}

UpdateGame(gameid:number){
  this.gameservice.getgame(gameid).subscribe(game=>{
    this.currentGame = game;
    this.gameservice.currentGame = game;
    this.game_name = game.game_name;
  })
}
UpdateTiles(id:number){
  let result: Tile[] =[];
  this.tileservice.getTiles(id).subscribe(tiles =>{
    this.water_list=this.setList("water",tiles);
    this.tree_list=this.setList("tree",tiles);
    tiles.forEach(elt =>{
    var tile= elt as Tile;
    tile.type = 'assets/'+tile.type.toLowerCase()+'.svg';
    result.push(tile);  
  });
  });
  return result;
}
UpdatePlayer(id:number){ 
  this.playerservice.getPlayer(id).subscribe(player=>{
    this.user_name = player.user_name;
  });
}
UpdatePieces(id:number){
  this.pieceservice.getPieces(id).subscribe(pieces =>{
    pieces.forEach(elt =>{
      var piece = elt as Piece;
      piece.type = 'assets/'+piece.type.toLowerCase()+'.svg';
      this.pieces.push(piece);
    })
  });
}
UpdateMap(id:number){
  this.mapservice.getmap(id).subscribe(map => {
    this.score = map.score;
    this.turn = map.turn;
    this.score_limit = map.score_limit;
    this.nb_free_circus = 0;
    this.nb_free_fountain = 0;
    this.nb_free_house = 1;
    this.nb_free_windturbine = 0;
  });
}
/* Compute and display score*/
  intScore(){
    if (this.score_limit==10){
      this.progressBarScore= (this.score/this.score_limit)*100;
    }else{
      this.progressBarScore = ((this.score-(this.score_limit-10*this.turn))/(this.score_limit-(this.score_limit-10*this.turn)))*100;
    }
  }
  getProgressBarScore(){
    return this.progressBarScore;
  }

  getStringScore(){
    return this.score.toString()+"/"+this.score_limit.toString();
  }
/* get the tile's score*/
  getTiles(num:number) : string{
    return this.tiles[num].type;
  }

  setTiles(str:string){
    var list = Array.from(document.getElementsByClassName(this.anc_class));
      for(let i=0;i<list.length;i++){
      if(list[i].getAttribute('src')=='assets/empty.svg'){
          list[i].className=str;
      }else if(list[i].getAttribute('src')=='assets/water.svg'){
          list[i].className='water';
      }else{
          list[i].className='tree';
      }
   }
  }
/* click interactions */
clickTile(x:number,y:number){
  if(this.current_type != -1){
    if(!this.checkTreeNWaterNPosee(x,y)){  
    const re=/\w+_(\w+)/;
    const tile_type= this.anc_class.replace(re,'$1');    
  if(tile_type!='tile' && tile_type!='origin'){
    var tile = new Tile();
    tile.loct_x = x;
    tile.loct_y = y;
    let temp=0;
    const pieces_tab =[this.nb_free_fountain,this.nb_free_house,this.nb_free_windturbine,this.nb_free_circus]
    switch (this.current_type){
      case 1 :
        tile.type = 'assets/fountain.svg';
        this.nb_free_fountain--;
        temp = this.nb_free_fountain;
        break;
      case 2 :
        tile.type = 'assets/house.svg';
        this.nb_free_house--;
        temp = this.nb_free_house;
        break;
      case 3 :
        tile.type = 'assets/wind_turbine.svg';
        this.nb_free_windturbine--;
        temp = this.nb_free_windturbine;
        break;
      case 4 : 
        tile.type = 'assets/circus.svg';
        this.nb_free_circus--;
        temp = this.nb_free_circus;
        break;
      }
      if(temp ==0 ){
        this.setTiles('origin');
        this.anc_class='origin';
        this.current_type=-1;
      }else{
        this.setTiles(this.anc_class);
      }
    this.pieces_posee_list.push(tile);
    const anc_score = this.score;
    const anc_score_limit = this.score_limit;
    this.computeScore(x,y,tile.type);
    this.computeScoreLimit();
    const newBlock = new ClickTile(this.gameservice,x,y,tile.type,anc_score,anc_score_limit,this.score,this.score_limit,pieces_tab, this.turn);
    newBlock.execute();
    this.UndoHistory.push(newBlock);
    if(this.RedoHistory.length != 0){
      this.RedoHistory = [];
    }
    this.tiles=this.UpdateTiles(this.gameid);
    } 
  }
  }
  this.getNlength();
  this.endGame();
}
 clickFountain(){
   if(this.nb_free_fountain>0){
    this.current_type = 1;
    this.setTiles('tile_fountain');
    this.anc_class='tile_fountain';
   }
 }
 clickHouse(){
  if(this.nb_free_house>0){
    this.current_type = 2;
    this.setTiles('tile_house');
    this.anc_class='tile_house';
  }
}
clickWind(){
  if(this.nb_free_windturbine>0){
    this.current_type = 3;
    this.setTiles('tile_wind_turbine');
    this.anc_class='tile_wind_turbine';
  }
}
clickCircus(){
  if(this.nb_free_circus>0){
    this.current_type = 4;
    this.setTiles('tile_circus');
    this.anc_class='tile_circus';
  }
}
checkTreeNWaterNPosee(x:number,y:number){
  var result = false;
  this.water_list.forEach(elt=>{
    if((elt.loct_x == x) && (elt.loct_y == y)&&(!result)){
      result = true;
    }
  });
  if(!result){
    this.tree_list.forEach(elt =>{
      if((elt.loct_x == x) && (elt.loct_y == y)&&(!result)){
        result = true;
      }
    });
  }
    if(!result){
      this.pieces_posee_list.forEach(elt =>{
        if((elt.loct_x == x) && (elt.loct_y == y)&&(!result)){
          result = true;
        }
      });
    }
  return result;
}


/* UNDO & REDO */
clickUndo(){
  if(this.UndoHistory.length != 0){
    const undoBlock = this.UndoHistory.pop()!; 
    undoBlock.undo();
    this.score = undoBlock.previous_score;
    this.score_limit = undoBlock.previous_score_limit;
    this.nb_free_fountain = undoBlock.previous_nb_pieces[0];
    this.nb_free_house = undoBlock.previous_nb_pieces[1];
    this.nb_free_windturbine = undoBlock.previous_nb_pieces[2];
    this.nb_free_circus = undoBlock.previous_nb_pieces[3];
    this.turn = undoBlock.previous_turn;
    this.intScore();
    this.pieces_posee_list.pop();
    this.tiles=this.UpdateTiles(this.gameid);
    this.RedoHistory.push(undoBlock);
    }
}
clickRedo(){
  if(this.RedoHistory.length != 0){
    const redoBlock = this.RedoHistory.pop()!;
    redoBlock.redo();
    switch(redoBlock.previous_type){
      case 'assets/fountain.svg':
          this.nb_free_fountain--;
          break;
      case 'assets/house.svg':
          this.nb_free_house--;
          break;
      case 'assets/wind_turbine.svg':
          this.nb_free_windturbine--;
          break;
      case 'assets/circus.svg':
          this.nb_free_circus--;
          break;   
  }
  var tile = new Tile();
  tile.loct_x = redoBlock.previous_x;
  tile.loct_y = redoBlock.previous_y;
  tile.type = redoBlock.previous_type;
  this.pieces_posee_list.push(tile);
  this.computeScore(redoBlock.previous_x,redoBlock.previous_y,redoBlock.previous_type);
  this.computeScoreLimit();
  this.tiles=this.UpdateTiles(this.gameid);
  this.UndoHistory.push(redoBlock);
  }
}
/* end the game */
endGame(){
  if(this.nb_free_fountain==0 && this.nb_free_house==0 && this.nb_free_circus==0 && this.nb_free_windturbine==0){
    this.openExitDialog();
  }
  else{
    for( let i=0;i<this.tiles.length;i++){
      if(this.tiles[i].type.toLowerCase()!='empty'){
        break;
      }
      else this.openExitDialog();
    }
  }
}
/* update the tiles' stock*/
stock(){
  this.nb_placements++;
  this.nb_free_fountain++;
  this.nb_free_circus++;
  this.nb_free_house++;
  this.nb_free_windturbine++;
}

tileType(place:number){
  const re=/\w+\/(\w+).svg/;
  const type = this.tiles[place].type.replace(re,'$1');
  return type;
}

  getRadius(tile : string){
    if (tile=='assets/house.svg' || tile=='assets/fountain.svg' || tile=='assets/wind_turbine.svg'){
        return 1;
    }
    else {
      return 3;
    }
  }

  computeNeighbourTiles(centerI: number, centerJ: number, tile : string): void {
    const radius = this.getRadius(tile);
    this.neighbourTiles=[];
    var result : [string, number, number, number][] =[];
    // All possible shifts regarding the reference Tile
    if((tile=='assets/house.svg') || (tile=='assets/wind_turbine.svg') ||(tile=='assets/circus.svg') ||(tile=='assets/fountain.svg')){
      if (tile=='assets/house.svg'){
        for (let i: number = -radius; i <= radius; i++) {
            for (let j: number = -radius; j <= radius ; j++) {
                const absoluteI: number = centerI + i;
                const absoluteJ: number = centerJ + j;

                // The tiles must be in the grid   
                if (absoluteI >= 0 && absoluteI <= 9 && absoluteJ >= 0 && absoluteJ <= 9) {
                    // The tile must not be the reference Tile
                    if ((absoluteI !== centerI) || (absoluteJ !== centerJ)) {
                        if(this.computeScoreHouse(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ))!=0){
                            this.neighbourTiles.push([this.getTiles(absoluteI* 10 + absoluteJ), absoluteI, absoluteJ,this.computeScoreHouse(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ)).toString()]);
                    }
                  }
                }

            }
        }
      } 
      if (tile=='assets/fountain.svg'){
        for (let i: number = -radius; i <= radius; i++) {
            for (let j: number = -radius; j <= radius ; j++) {
                const absoluteI: number = centerI + i;
                const absoluteJ: number = centerJ + j;

                // The tiles must be in the grid   
                if (absoluteI >= 0 && absoluteI <= 9 && absoluteJ >= 0 && absoluteJ <= 9) {
                    // The tile must not be the reference Tile
                    if ((absoluteI !== centerI) || (absoluteJ !== centerJ)) {
                      if(this.computeScoreFountain(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ))!=0){

                        this.neighbourTiles.push([this.getTiles(absoluteI* 10 + absoluteJ), absoluteI, absoluteJ,this.computeScoreFountain(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ)).toString()]);
                    
                      } 
                    }
                }

            }
        }
      } 
      if (tile=='assets/circus.svg'){
        for (let i: number = -radius; i <= radius; i++) {
            for (let j: number = -radius; j <= radius ; j++) {
                const absoluteI: number = centerI + i;
                const absoluteJ: number = centerJ + j;

                // The tiles must be in the grid   
                if (absoluteI >= 0 && absoluteI <= 9 && absoluteJ >= 0 && absoluteJ <= 9) {
                    // The tile must not be the reference Tile
                    if ((absoluteI !== centerI) || (absoluteJ !== centerJ)) {
                      if(this.computeScoreCircus(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ))!=0){
                        this.neighbourTiles.push([this.getTiles(absoluteI* 10 + absoluteJ), absoluteI, absoluteJ,this.computeScoreCircus(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ)).toString()]);
                    }
                  }
                }

            }
        }
      } 
      if (tile=='assets/wind_turbine.svg'){
        for (let i: number = -radius; i <= radius; i++) {
            for (let j: number = -radius; j <= radius ; j++) {
                const absoluteI: number = centerI + i;
                const absoluteJ: number = centerJ + j;

                // The tiles must be in the grid   
                if (absoluteI >= 0 && absoluteI <= 9 && absoluteJ >= 0 && absoluteJ <= 9) {
                    // The tile must not be the reference Tile
                    if ((absoluteI !== centerI) || (absoluteJ !== centerJ)) {
                      if(this.computeScoreWind(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ))!=0){
                        this.neighbourTiles.push([this.getTiles(absoluteI* 10 + absoluteJ), absoluteI, absoluteJ,this.computeScoreWind(absoluteI,absoluteJ,this.getTiles(absoluteI* 10 + absoluteJ)).toString()]);
                    }
                  }
                }
            }
        }
      }
    }
    else{
      this.neighbourTiles=[["",0,0,""]];
    } 
}

  computeScoreHouse(x:number,y:number,tile : string): number{
        if(tile=='assets/tree.svg'){
            return 5;
          }
          if(tile=='assets/circus.svg'){
            return 10;
            
          }
          if(tile=='assets/fountain.svg'){
             return 8;
          }
          if(tile=='assets/house.svg'){
           return -1;
          }
          if(tile=='assets/windturbine.svg'){
            return -12;
          }
          return 0;
        }
      computeScoreFountain(x:number,y:number, tile : string): number{
              if(tile=='assets/house.svg'){
                return 8;
              }
              if(tile=='assets/circus.svg'){
                return 6;
              }
           return 0;
      }

      computeScoreCircus(x:number,y:number, tile : string): number{
              if(tile=='assets/circus.svg'){
                return -25;
              }
              if(tile=='assets/house.svg'){
                return 15;
              }
              return 0;
            }

      computeScoreWind(x:number,y:number, tile : string): number{
              if(tile=='assets/tree.svg'){
                return -4;
              }
              if(tile=='assets/house.svg'){
                return -8;
              }
              if(tile=='assets/water.svg'){
                return 10;
              }
              return 0;
            }
  computeScore(x:number,y:number, type:string){
  this.computeNeighbourTiles(x,y, type);
  if(type=='assets/house.svg'){
    this.score=this.score+6; 
  }
  else if(type=='assets/circus.svg'){
    this.score=this.score+8; 
  }
  else if(type=='assets/fountain.svg'){
    this.score=this.score+6; 
  }
  else if(type=='assets/wind_turbine.svg'){
    this.score=this.score+15; 
  }

  for (let j : number=0;j<this.neighbourTiles.length;j++){
    this.score=this.score+ Number(this.neighbourTiles[j][3]);
  }
}
      displayScores(x: number, y: number): void {
        if (this.getTiles(10*x+y)=="assets/empty.svg"){
        let i = this.current_type;
        let type ="";
        switch(i){
          case 1 : 
            type="assets/fountain.svg";
            break;
          case 2 : 
            type="assets/house.svg";
            break;
          case 3 : 
            type="assets/wind_turbine.svg";
            break;
          case 4 : 
            type="assets/circus.svg";
            break;
            
        }
        const radius = this.getRadius(type);
        this.computeNeighbourTiles(x, y, type);
        }
      }
/* hover tile -> score's interactions*/
displayMainScore() {
    this.getNlength();
        var list = Array.from(document.getElementsByClassName(this.anc_class));
        for(let i=0;i<list.length;i++){
           if(this.current_type==1){
             return 6;
           }
           else if(this.current_type==2){
            return 6;
          }
          else if(this.current_type==3){
            return 15;
          }
          else if(this.current_type==4){
            return 8;
          }
        }
            return 0;
  }

  resetTiles(lists: Array<Element>[]){
  lists.forEach(e=>e.forEach(f=>f.className=this.anc_class));
  }
  computeScoreLimit() {
      this.intScore();
      if (this.score>=this.score_limit){
        while(this.score>=this.score_limit){
          this.progressBarScore=100;
          this.turn++;
          this.score_limit= this.score_limit + 10 * this.turn;
          this.stock();
          this.intScore();
        }
      }
}
  getNScore(i: number){
      this.getNlength();
      if(this.neighbourTiles==[["",0,0,0]]){
        return "";
      }
      else return this.neighbourTiles[i][3].toString();
  }
  getNlength(): void{
    this.array=[]
    var temp:number[] = new Array(this.neighbourTiles.length);
    for(var i =0; i<temp.length;i++){
        temp[i]=i;
    }
    this.array=Array.from(temp);
  }
  resetScore(){
    this.neighbourTiles=[["",0,0,""]];
  }
  modifyUserName(){
    this.router.navigate(['player/'+this.playerid+'/'+this.gameid+'/'+this.mapname]);
  }
      /*open the classement window*/
openDialog(): void {
  const dialogRef = this.dialog.open(DialogComponent, {
    data: {
      gamename: this.game_name,
      mapname : this.mapname
    }
  
  });

  dialogRef.afterClosed().subscribe(result => {
  });
}
/*open the exit window*/
openExitDialog(): void {
  //this.gameservice.updateScore(this.gameid,this.score);
  const dialogRef = this.exitdialog.open(ExitdialogComponent, {
    width: '0px',
    height : '0px',
    data: {
      mapname: this.mapname,
      playername: this.user_name,
      scorefinal :this.score
    }
    
  });
  

  dialogRef.afterClosed().subscribe(result => {
  });
}

}
