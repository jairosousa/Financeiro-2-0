import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFormaPagamento } from 'app/shared/model/forma-pagamento.model';
import { FormaPagamentoService } from './forma-pagamento.service';

@Component({
    selector: 'jhi-forma-pagamento-update',
    templateUrl: './forma-pagamento-update.component.html'
})
export class FormaPagamentoUpdateComponent implements OnInit {
    formaPagamento: IFormaPagamento;
    isSaving: boolean;

    constructor(protected formaPagamentoService: FormaPagamentoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ formaPagamento }) => {
            this.formaPagamento = formaPagamento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.formaPagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.formaPagamentoService.update(this.formaPagamento));
        } else {
            this.subscribeToSaveResponse(this.formaPagamentoService.create(this.formaPagamento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormaPagamento>>) {
        result.subscribe((res: HttpResponse<IFormaPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
