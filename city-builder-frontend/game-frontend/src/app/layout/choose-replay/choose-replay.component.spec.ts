import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseReplayComponent } from './choose-replay.component';

describe('ChooseReplayComponent', () => {
  let component: ChooseReplayComponent;
  let fixture: ComponentFixture<ChooseReplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseReplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseReplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
