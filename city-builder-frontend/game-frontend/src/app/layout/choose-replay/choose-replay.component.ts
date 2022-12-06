import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Game } from 'src/app/models/game.model';
import { GameService } from 'src/app/services/Game/game.service';

@Component({
  selector: 'app-choose-replay',
  templateUrl: './choose-replay.component.html',
  styleUrls: ['./choose-replay.component.css']
})
export class ChooseReplayComponent implements OnInit {
  games!:Game[];
  gameid:number;
  constructor(private gameservice:GameService,private router: Router) {
    this.gameid=0;
   }

  ngOnInit(): void {
    this.gameservice.getgames().subscribe(games=>{
      this.games=games;
    });
    const selectElement = document.querySelector('.game');
    selectElement?.addEventListener('change',(event)=>{
    this.gameid = parseInt((<HTMLSelectElement>event.target).value);
    this.gameservice.getgame(this.gameid).subscribe(player =>{
      console.log(player);
    });
  })
  }
  ToReplay(){
    this.router.navigate(['replay/'+this.gameid]);
  }

}
