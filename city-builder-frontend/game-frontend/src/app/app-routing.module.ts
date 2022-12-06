import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChooseReplayComponent } from './layout/choose-replay/choose-replay.component';
import { GameComponent } from './layout/game/game.component';
import { HomeComponent } from './layout/home/home.component';
import { PlayerComponent } from './layout/player/player.component';
import { ReplayComponent } from './layout/replay/replay.component';

const routes: Routes = [
{ path : 'home',component: HomeComponent},
{ path: 'game',component: GameComponent},
{ path: 'game/:mapname/:gameid/:playerid',component: GameComponent},
{path: 'player/:playerid/:gameid/:mapname', component: PlayerComponent},
{path: 'chooseReplay', component: ChooseReplayComponent},
{path: 'replay/:gameid', component: ReplayComponent},
{ path: '',
  redirectTo: '/home',
  pathMatch: 'full'
}];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
