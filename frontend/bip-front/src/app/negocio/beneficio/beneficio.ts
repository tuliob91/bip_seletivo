import { Component, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { BeneficioService } from './service/beneficio.service';
import { BeneficioModel } from './model/beneficio-model';
import { CommonModule } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BeneficioShow } from './beneficio-show/beneficio-show';
import { switchMap, timer } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-beneficio',
  imports: [CommonModule],
  templateUrl: './beneficio.html',
  styleUrl: './beneficio.css',
})
export class Beneficio implements OnInit {

  beneficiolist: BeneficioModel[] = [];

  constructor(private benefService: BeneficioService, private modalService: NgbModal,private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    timer(0, 5000).pipe(
      switchMap(() => this.benefService.listarTodos())
    ).subscribe(data => {
      if (data) {
        this.beneficiolist = data;
        this.cdr.detectChanges();
      }
    });
  }

  carregarDados() {
    this.benefService.listarTodos().subscribe(data => {
      if (data) {
        this.beneficiolist = data
      }
    })
  }

  beneficioShow(idBenef: number | undefined, tpOper: string) {
    const moddalRef = this.modalService.open(BeneficioShow);
    moddalRef.componentInstance.tipoOperacao = tpOper;
    moddalRef.componentInstance.idBeneficio = idBenef ? String(idBenef) : undefined;
    moddalRef.result.then((resultado) => {
      if (resultado && resultado == 'sucesso') {
        this.carregarDados()
      }
    })
  }
}
