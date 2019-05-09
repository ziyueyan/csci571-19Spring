import { TestBed } from '@angular/core/testing';

import { WishServiceService } from './wish-service.service';

describe('WishServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WishServiceService = TestBed.get(WishServiceService);
    expect(service).toBeTruthy();
  });
});
