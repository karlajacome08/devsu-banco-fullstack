import { TestBed } from '@angular/core/testing';

import { ReporteService } from './reporte';
import { provideHttpClient } from '@angular/common/http';

describe('Reporte', () => {
  let service: ReporteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()],
    });
    service = TestBed.inject(ReporteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
