import { TileService } from "../services/Tile/tile.service";
import { Piece } from "./piece.model";
import { Tile } from "./tile.model";

export class Map{
 name:string;
 pieces :Piece[];
 tiles: Tile[]; 
 score: number;
 score_limit: number;
 turn : number;
 current_type: string;
}