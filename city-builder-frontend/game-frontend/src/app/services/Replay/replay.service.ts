import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Replay} from '../../models/replay.model';

@Injectable({
  providedIn: 'root'
})
export class ReplayService {
 
  constructor(private http: HttpClient) { 
   
  }
  
  getGameReplay(gameid:number){
    return this.http.get<Replay[]>('city/replay/'+gameid); 
  }

}
