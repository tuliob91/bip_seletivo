import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BeneficioModel } from '../model/beneficio-model';
import { BeneficioService } from '../service/beneficio.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-beneficio-show',
  imports: [FormsModule, CommonModule],
  templateUrl: './beneficio-show.html',
  styleUrl: './beneficio-show.css',
})
export class BeneficioShow implements OnInit, OnChanges {

  @Input() tipoOperacao: string | undefined;
  @Input() idBeneficio: string | undefined;

  editando: boolean = false;
  titulo: string = "";

  beneficio: any;
  carregando: boolean = true;
  constructor(public activeModal: NgbActiveModal, private service: BeneficioService, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    console.log('this.tipoOperacao,this.idBeneficio:', this.tipoOperacao, this.idBeneficio)
    this.definirContexto();
    if (this.idBeneficio && this.idBeneficio !== 'undefined') {
      this.carregarDados();
    } else {
      // 2. Se for NOVO, resetamos o objeto e avisamos que o carregamento acabou
      this.beneficio = { nome: '', descricao: '',  ativo: true };
      this.carregando = false; // Isso faz o spinner sumir!
      this.cdr.detectChanges();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('this.tipoOperacao,this.idBeneficio:', this.tipoOperacao, this.idBeneficio)
    if (changes['tipoOperacao'] || changes['idBeneficio']) {
      this.definirContexto();
    }
  }
  private carregarDados() {
    console.log("Carregar dados")
    this.service.buscarPorId(Number(this.idBeneficio)!).subscribe({
      next: (res) => {
        console.log('Chegou:', res);
        this.beneficio = res;
        this.carregando = false; // Dados chegaram
        this.cdr.detectChanges(); // Força a atualização da tela
      },
      error: (err) => {
        console.error(err);
        this.carregando = false;
      }
    });
  }
  salvar() {
    this.service.salvar(this.beneficio).subscribe({
      next: (res) => {
        this.activeModal.close()
      }, error: (res) => {
        this.activeModal.close(res.error)
      },
    });

  }

  private definirContexto() {
    if (this.tipoOperacao == 'show') {
      this.titulo = 'Detalhes do Benefício';
      this.editando = false;
    } else {
      this.editando = true;
      this.titulo = this.idBeneficio ? 'Editar Benefício' : 'Novo Benefício';
    }
  }
}
