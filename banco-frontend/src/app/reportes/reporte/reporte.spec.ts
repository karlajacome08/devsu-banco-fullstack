import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Reporte } from './reporte';
import { provideHttpClient } from '@angular/common/http';

describe('Reporte', () => {
  let component: Reporte;
  let fixture: ComponentFixture<Reporte>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Reporte],
      providers: [provideHttpClient()],
    }).compileComponents();

    fixture = TestBed.createComponent(Reporte);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
