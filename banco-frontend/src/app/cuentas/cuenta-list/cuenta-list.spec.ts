import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentaList } from './cuenta-list';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('CuentaList', () => {
  let component: CuentaList;
  let fixture: ComponentFixture<CuentaList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuentaList],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(CuentaList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
