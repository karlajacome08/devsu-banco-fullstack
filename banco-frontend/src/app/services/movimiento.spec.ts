import { TestBed } from '@angular/core/testing';

import { MovimientoService } from './movimiento';
import { provideHttpClient } from '@angular/common/http';

describe('Movimiento', () => {
  let service: MovimientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()],
    });
    service = TestBed.inject(MovimientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
