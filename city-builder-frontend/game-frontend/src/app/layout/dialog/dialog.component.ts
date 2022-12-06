import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog'; 
import {MatDialogModule} from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Top5Score } from 'src/app/models/top5score.model';
import { PlayerService } from 'src/app/services/Player/player.service';
import {GameComponent} from '../game/game.component';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent{
  message : string= "";
  mapname!: string;
  top5score: Top5Score[];
  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,private playerservice:PlayerService, 
    @Inject(MAT_DIALOG_DATA) public data: any) { 
      this.message = this.data.gamename;
      this.mapname = this.data.mapname;
      console.log(this.message);
      console.log(this.mapname);
      this.top5score = [];
      this.getTop5Score(this.mapname);
    }
    getTop5Score(mapname: string){
      this.playerservice.getTop5Score(mapname).then(allMapScore =>
        {console.log(allMapScore);
        let tab = allMapScore as Top5Score [];
        let soluce : Top5Score[]=[];
        let bestscore: number = -1;
        while(tab.length>0){
            bestscore=-1;
            let result:Top5Score=new Top5Score();
            let indice:number=0;
            console.log(result);
            for(let i=0;i<tab.length;i++){
              if(tab[i].score > bestscore){
                indice=i;
                bestscore=tab[i].score;
                result = tab[i];
              }
            }
            soluce.push(result);
            tab.splice(indice,1);
        }
        while(soluce.length>5){
          soluce.pop();
        }
        this.top5score = soluce;
        console.log(this.top5score);  
    });
    }
  }
