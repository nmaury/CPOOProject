import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Replay } from 'src/app/models/replay.model';
import { ReplayService } from 'src/app/services/Replay/replay.service';

@Component({
  selector: 'app-replay',
  templateUrl: './replay.component.html',
  styleUrls: ['./replay.component.css']
})
export class ReplayComponent implements OnInit {
  replay!:Replay[];
  avancement : number;
  user_name!: string;
  game_name!: string;
  constructor(private replayservice: ReplayService, private route:ActivatedRoute,private router:Router) {
    this.avancement=0;
   }

  ngOnInit(): void {
    this.route.params.subscribe(params=>{
      this.replayservice.getGameReplay(params.gameid).subscribe(replay => {
        replay.forEach(frame => {
          frame.tiles.forEach(elt => {
            elt.type = 'assets/'+elt.type.toLowerCase()+'.svg';
          });
        });
        this.replay = replay;
        console.log(replay);
        this.user_name = this.replay[0].playername;
        this.game_name= this.replay[0].gamename;
      });
    }) 
  }
  

  getTiles(place:number){
    return this.replay[this.avancement].tiles[place].type;
  }

  getStringScore(){
    return this.replay[this.avancement].score.toString()+"/"+this.replay[this.avancement].score_limit.toString();
  }
  getProgressBarScore(){
    return ( (this.replay[this.avancement].score/this.replay[this.avancement].score_limit)*100);
  }
  
  clickBack(){
    if(this.avancement >0){
    this.avancement--;
    }
  }
  clickForward(){
    if(this.avancement < this.replay.length-1){
      this.avancement++;
    }
  }
  clickEnd(){
    this.router.navigate(['home']);
  }
}
