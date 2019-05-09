import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPagesComponent } from './detail-pages.component';

describe('DetailPagesComponent', () => {
  let component: DetailPagesComponent;
  let fixture: ComponentFixture<DetailPagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailPagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailPagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
