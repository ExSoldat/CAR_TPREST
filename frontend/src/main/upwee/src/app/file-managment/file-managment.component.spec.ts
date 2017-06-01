import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileManagmentComponent } from './file-managment.component';

describe('FileManagmentComponent', () => {
  let component: FileManagmentComponent;
  let fixture: ComponentFixture<FileManagmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileManagmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
