import { TestBed } from '@angular/core/testing';

import { EnmProjectService } from './enm-project.service';

describe('EnmProjectService', () => {
  let service: EnmProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnmProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
