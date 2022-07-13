import { TestBed } from '@angular/core/testing';

import { AddfriendService } from './addfriend.service';

describe('AddfriendService', () => {
  let service: AddfriendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddfriendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
