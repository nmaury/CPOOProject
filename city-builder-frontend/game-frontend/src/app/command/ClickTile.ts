import { UndoableCommand } from "interacto";
import { GameComponent } from "../layout/game/game.component";
import { Game } from "../models/game.model";
import { Map } from "../models/map.model";
import { Tile } from "../models/tile.model";
import { GameService } from "../services/Game/game.service";
import { MapService } from "../services/Map/map.service";

export class ClickTile extends UndoableCommand{
    
    previous_score!: number;
    previous_score_limit!: number;
    previous_nb_pieces! : number[];
    previous_x!: number;
    previous_y!: number;
    previous_type!: string;
    previous_turn!: number;
    public constructor(private gameservice:GameService,private x:number,private y:number,private type:string,private score: number,private score_limit: number,private newscore:number,private newscore_limit:number ,private nb_pieces: number[], private turn:number){
        super();

    }
    
    protected createMemento(): void {
        //console.log("creation!");
        this.previous_nb_pieces = this.nb_pieces;
        this.previous_score = this.score;
        this.previous_score_limit = this.score_limit;
        this.previous_x= this.x;
        this.previous_y = this.y;
        this.previous_type = this.type;
        this.previous_turn = this.turn;
    }
    protected execution(): void {
        //console.log("execution!");
        let new_tile = new Tile();
        new_tile.loct_x = this.x;
        new_tile.loct_y = this.y;
        new_tile.type = this.type;
       // console.log(new_tile);
        this.gameservice.setTile(10*this.x+this.y,new_tile,this.newscore, this.newscore_limit);

    }
    
    public undo(): void {
       this.gameservice.removeTile(10*this.x+this.y,this.previous_score, this.previous_score_limit);
   }
    
    public redo(): void {
            this.execution();
    }
}