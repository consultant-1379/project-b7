import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumberStatsComponent } from './number-stats.component';

describe('NumberStatsComponent', () => {
  let component: NumberStatsComponent;
  let fixture: ComponentFixture<NumberStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumberStatsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NumberStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
