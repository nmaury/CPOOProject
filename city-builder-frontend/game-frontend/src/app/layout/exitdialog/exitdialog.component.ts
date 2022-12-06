import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog'; 
import {MatDialogModule} from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { GameService } from 'src/app/services/Game/game.service';

@Component({
  selector: 'app-exitdialog',
  templateUrl: './exitdialog.component.html',
  styleUrls: ['./exitdialog.component.css']
})
export class ExitdialogComponent implements OnInit {
  playername : string = "";
  scorefinal : number = 0;
  mapname : string = "";
  constructor(
    public dialogRef: MatDialogRef<ExitdialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private router: Router, private gameservice:GameService) { 
      this.mapname = this.data.mapname;
      this.playername = this.data.playername;
      this.scorefinal = this.data.scorefinal;
      console.log(this.playername);
    }

  ngOnInit(): void {
      
  }
  OnClick(){
    this.gameservice.endGame(this.mapname,this.scorefinal,this.playername);
    this.dialogRef.close();
    this.router.navigate(['home']);
  }
}
