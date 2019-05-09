import { TestBed } from '@angular/core/testing';

import { ShipServiceService } from './ship-service.service';

describe('ShipServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ShipServiceService = TestBed.get(ShipServiceService);
    expect(service).toBeTruthy();
  });
});
