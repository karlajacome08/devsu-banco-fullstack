import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimientoList } from './movimiento-list';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('MovimientoList', () => {
  let component: MovimientoList;
  let fixture: ComponentFixture<MovimientoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovimientoList],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(MovimientoList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
