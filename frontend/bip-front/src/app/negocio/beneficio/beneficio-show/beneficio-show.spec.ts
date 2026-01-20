import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioShow } from './beneficio-show';

describe('BeneficioShow', () => {
  let component: BeneficioShow;
  let fixture: ComponentFixture<BeneficioShow>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioShow]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficioShow);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
