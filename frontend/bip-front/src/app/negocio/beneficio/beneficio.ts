import { Component, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { BeneficioService } from './service/beneficio.service';
import { BeneficioModel } from './model/beneficio-model';
import { CommonModule } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BeneficioShow } from './beneficio-show/beneficio-show';
import { switchMap, timer } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';
import { BeneficioTransfer } from './beneficio-transfer/beneficio-transfer';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-beneficio',
  imports: [CommonModule, NgbToastModule],
  templateUrl: './beneficio.html',
  styleUrl: './beneficio.css',
})
export class Beneficio implements OnInit {

  beneficiolist: BeneficioModel[] = [];
  showToast = false;
  toastMessage = '';
  toastClass = '';
  constructor(private benefService: BeneficioService, private modalService: NgbModal, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    timer(0, 1500).pipe(
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
      if (resultado) {
        this.carregarDados()
        this.exibirToast(resultado, true)
      } else {
        this.exibirToast("Registro Salvo com Sucesso!")
      }
    })
  }

  transferirBeneficio() {
    const modalRef = this.modalService.open(BeneficioTransfer);
    modalRef.result.then((resultado) => {
      this.cdr.detectChanges();
      console.log("RESULTADO FECHOU MODAL:", resultado)
      if (resultado) {
        this.carregarDados()
        this.exibirToast(resultado, true)
      } else {
        this.carregarDados()
        this.exibirToast("Transferência Efetuada Com Sucesso!")
      }
    })
  }

  exibirToast(mensagem: string, erro: boolean = false) {
    setTimeout(() => {
      this.showToast = true;
      this.toastMessage = mensagem;
      this.toastClass = erro ? 'bg-warning text-dark' : 'bg-success text-dark';
      this.cdr.detectChanges();
      setTimeout(() => {
        this.showToast = false;
        this.cdr.detectChanges();
      }, 3000);
    }, 0);
  }

  excluir(idExcl: number | undefined) {
    if (idExcl) {
      this.benefService.excluir(idExcl).subscribe({
        next: (res) => {
          this.exibirToast("Registro Removido com Successo!")
        }, error: (res) => {
          this.exibirToast("Não foi possível remover Registro!", true)
        },
      })
    }
  }
}
