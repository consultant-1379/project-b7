import { TestBed } from '@angular/core/testing';

import { CnaService } from './cna.service';

describe('CnaService', () => {
  let service: CnaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CnaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
