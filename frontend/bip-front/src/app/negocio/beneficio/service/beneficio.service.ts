import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BeneficioModel } from '../model/beneficio-model';

@Injectable({
    providedIn: 'root'
})
export class BeneficioService {
    private readonly API_URL = 'http://localhost:8081/api/v1/beneficios';

    constructor(private http: HttpClient) { }

    listarTodos(): Observable<BeneficioModel[]> {
        console.log("Entrou em LISTAR TODOS");
        return this.http.get<BeneficioModel[]>(`${this.API_URL}/listaBeneficios`)
            .pipe(catchError(this.handleError));
    }
    buscarPorId(id: number): Observable<BeneficioModel> {
        return this.http.get<BeneficioModel>(`${this.API_URL}/getBeneficio/${id}`)
            .pipe(catchError(this.handleError));
    }
    salvar(beneficio: BeneficioModel): Observable<BeneficioModel> {
        return this.http.post<BeneficioModel>(`${this.API_URL}/salvar`, beneficio)
            .pipe(catchError(this.handleError));
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/excluir/${id}`)
            .pipe(catchError(this.handleError));
    }

    transferir(idOrigem: number, idDestino: number, valor: number): Observable<any> {
        const payload = { idOrigem, idDestino, valor };
        return this.http.post(`${this.API_URL}/transferir`, payload)
            .pipe(catchError(this.handleError));
    }
    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'Ocorreu um erro desconhecido';
        if (error.error instanceof ErrorEvent) {
            errorMessage = `Erro: ${error.error.message}`;
        } else {
            errorMessage = error.error || `Código do erro: ${error.status}`;
        }
        return throwError(() => errorMessage);
    }
}