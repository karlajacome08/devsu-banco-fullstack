import { TestBed } from '@angular/core/testing';

import { CuentaService } from './cuenta';
import { provideHttpClient } from '@angular/common/http';

describe('Cuenta', () => {
  let service: CuentaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()],
    });
    service = TestBed.inject(CuentaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
