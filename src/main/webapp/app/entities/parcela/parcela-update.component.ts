import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IParcela } from 'app/shared/model/parcela.model';
import { ParcelaService } from './parcela.service';
import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from 'app/entities/lancamento';
import { IFormaPagamento } from 'app/shared/model/forma-pagamento.model';
import { FormaPagamentoService } from 'app/entities/forma-pagamento';

@Component({
    selector: 'jhi-parcela-update',
    templateUrl: './parcela-update.component.html'
})
export class ParcelaUpdateComponent implements OnInit {
    parcela: IParcela;
    isSaving: boolean;

    lancamentos: ILancamento[];

    formapagamentos: IFormaPagamento[];
    dataVencimentoDp: any;
    dataPagamentoDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected parcelaService: ParcelaService,
        protected lancamentoService: LancamentoService,
        protected formaPagamentoService: FormaPagamentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parcela }) => {
            this.parcela = parcela;
        });
        this.lancamentoService.query().subscribe(
            (res: HttpResponse<ILancamento[]>) => {
                this.lancamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.formaPagamentoService.query().subscribe(
            (res: HttpResponse<IFormaPagamento[]>) => {
                this.formapagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parcela.id !== undefined) {
            this.subscribeToSaveResponse(this.parcelaService.update(this.parcela));
        } else {
            this.subscribeToSaveResponse(this.parcelaService.create(this.parcela));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IParcela>>) {
        result.subscribe((res: HttpResponse<IParcela>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLancamentoById(index: number, item: ILancamento) {
        return item.id;
    }

    trackFormaPagamentoById(index: number, item: IFormaPagamento) {
        return item.id;
    }
}
