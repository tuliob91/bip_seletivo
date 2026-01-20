import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { BeneficioModel } from '../model/beneficio-model';
import { BeneficioService } from '../service/beneficio.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-beneficio-transfer',
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio-transfer.html',
  styleUrl: './beneficio-transfer.css',
})
export class BeneficioTransfer implements OnInit {

  beneficiolist: BeneficioModel[] = [];
  listaDestino: BeneficioModel[] = [];

  origemSelecionada?: BeneficioModel;
  destinoSelecionado?: BeneficioModel;
  valorTransferencia: any;

  constructor(private activeModal: NgbActiveModal, private service: BeneficioService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.service.listarTodos().subscribe(data => {
      this.beneficiolist = data;
      this.cdr.detectChanges();
    });
  }

  onOrigemChange() {
    this.listaDestino = this.beneficiolist.filter(b => b.id !== this.origemSelecionada?.id);

    if (this.destinoSelecionado?.id === this.origemSelecionada?.id) {
      this.destinoSelecionado = undefined;
    }
  }

  confirmarTransferencia() {
    if (this.valorTransferencia > (this.origemSelecionada?.saldo || 0)) {
      this.activeModal.close('Saldo insuficiente na origem!');
    } else {
      console.log(`Transferindo ${this.valorTransferencia} de ${this.origemSelecionada?.nome} para ${this.destinoSelecionado?.nome}`);
      if (this.origemSelecionada && this.origemSelecionada.id && this.destinoSelecionado && this.destinoSelecionado?.id) {
        this.service.transferir(this.origemSelecionada.id, this.destinoSelecionado?.id, this.valorTransferencia).subscribe({
          next: (res) => {
            this.activeModal.close()
          }, error: (res) => {
            this.activeModal.close(res.error)
          },
        })
      } else {
        this.activeModal.close("ERRO VALOR/VALORES EM BRANCO")
      }
    }
  }
  fechar() {
    this.activeModal.dismiss()
  }

}
