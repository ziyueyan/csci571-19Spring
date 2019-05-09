import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimilarTabComponent } from './similar-tab.component';

describe('SimilarTabComponent', () => {
  let component: SimilarTabComponent;
  let fixture: ComponentFixture<SimilarTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimilarTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimilarTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
