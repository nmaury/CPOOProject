import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { MatBadgeModule } from '@angular/material/badge';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GameComponent } from './layout/game/game.component';
import { GameService } from './services/Game/game.service';
import { MapService } from './services/Map/map.service';
import { PieceService } from './services/Piece/piece.service';
import { PlayerService } from './services/Player/player.service';
import { ReplayService } from './services/Replay/replay.service';
import { TileService } from './services/Tile/tile.service';
import { PlayerComponent } from './layout/player/player.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonBinderDirective, InteractoModule } from 'interacto-angular';
import {HttpClientModule} from '@angular/common/http';
import { HomeComponent } from './layout/home/home.component';

import { NgCircleProgressModule } from 'ng-circle-progress';
import { DialogComponent } from './layout/dialog/dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {  MatSnackBarModule} from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ExitdialogComponent } from './layout/exitdialog/exitdialog.component';
import { ChooseReplayComponent } from './layout/choose-replay/choose-replay.component';
import { ReplayComponent } from './layout/replay/replay.component';

@NgModule({
  exports: [
    MatDialogModule, 
    MatSnackBarModule
  ],
  imports: [BrowserAnimationsModule],
  declarations: [
    ExitdialogComponent,
    
  ]
})
export class MaterialModule {}
@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    PlayerComponent,
    HomeComponent,
    DialogComponent,
    ChooseReplayComponent,
    ReplayComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    MatBadgeModule,
    FormsModule,
    InteractoModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    BrowserAnimationsModule,
    NgCircleProgressModule.forRoot({
      // set defaults here
      "backgroundColor": "#9bd5e1",
      "backgroundStrokeWidth": 0,
      "backgroundPadding": 6,
      "radius": 69,
      "space": 6,
      "maxPercent": 100,
      "unitsColor": "#483500",
      "outerStrokeWidth": 10,
      "outerStrokeColor": "#FFFFFF",
      "innerStrokeColor": "#FFFFFF",
      "innerStrokeWidth": 7,
      "titleColor": "#ffffff",
      "titleFontSize": "24",
      "titleFontWeight": "500",
      "subtitleColor": "#483500",
      "animation": false,
      "animateTitle": false,
      "showSubtitle": false,
      "showUnits": false,
      "showInnerStroke": false,
      "startFromZero": false})  ],
  providers: [
    GameService,
    MapService,
    PieceService,
    PlayerService,
    ReplayService,
    TileService
    ],
  entryComponents:[MatDialogModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
