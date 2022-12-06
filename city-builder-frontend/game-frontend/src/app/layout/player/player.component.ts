import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Player } from 'src/app/models/player.model';
import { PlayerService } from 'src/app/services/Player/player.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  playerid:number;
  gameid: number;
  mapname: string;
  playerForm: FormGroup;
  constructor(private formBuilder: FormBuilder,private router: Router,private route: ActivatedRoute,private playerService: PlayerService) { 
  this.playerid=-1;
  this.mapname="";
  this.gameid=-1;
}
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.playerid = params.playerid;
      this.gameid=params.gameid;
      this.mapname=params.mapname;
      });
    this.playerForm = this.formBuilder.group({
      user_name: [null, Validators.required]
    });
  }
  ModifyPlayer(){
    const player = new Player(); 
    player.user_name = this.playerForm.get('user_name')?.value;
    player.id = this.playerid;
    console.log(player.user_name);
    console.log(this.playerid);
    this.playerService.modifyPlayer(player).then(()=>{
      this.router.navigate(['game/'+this.mapname+'/'+this.gameid+'/'+this.playerid]);
    });
  }

}
