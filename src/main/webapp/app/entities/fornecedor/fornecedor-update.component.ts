import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-fornecedor-update',
    templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
    fornecedor: IFornecedor;
    isSaving: boolean;

    enderecos: IEndereco[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected fornecedorService: FornecedorService,
        protected enderecoService: EnderecoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fornecedor }) => {
            this.fornecedor = fornecedor;
        });
        this.enderecoService.query({ filter: 'fornecedor-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.fornecedor.enderecoId) {
                    this.enderecos = res.body;
                } else {
                    this.enderecoService.find(this.fornecedor.enderecoId).subscribe(
                        (subRes: HttpResponse<IEndereco>) => {
                            this.enderecos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fornecedor.id !== undefined) {
            this.subscribeToSaveResponse(this.fornecedorService.update(this.fornecedor));
        } else {
            this.subscribeToSaveResponse(this.fornecedorService.create(this.fornecedor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
        result.subscribe((res: HttpResponse<IFornecedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }
}