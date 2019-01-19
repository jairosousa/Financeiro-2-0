import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FormaPagamento } from 'app/shared/model/forma-pagamento.model';
import { FormaPagamentoService } from './forma-pagamento.service';
import { FormaPagamentoComponent } from './forma-pagamento.component';
import { FormaPagamentoDetailComponent } from './forma-pagamento-detail.component';
import { FormaPagamentoUpdateComponent } from './forma-pagamento-update.component';
import { FormaPagamentoDeletePopupComponent } from './forma-pagamento-delete-dialog.component';
import { IFormaPagamento } from 'app/shared/model/forma-pagamento.model';

@Injectable({ providedIn: 'root' })
export class FormaPagamentoResolve implements Resolve<IFormaPagamento> {
    constructor(private service: FormaPagamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FormaPagamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FormaPagamento>) => response.ok),
                map((formaPagamento: HttpResponse<FormaPagamento>) => formaPagamento.body)
            );
        }
        return of(new FormaPagamento());
    }
}

export const formaPagamentoRoute: Routes = [
    {
        path: 'forma-pagamento',
        component: FormaPagamentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'financeiroApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forma-pagamento/:id/view',
        component: FormaPagamentoDetailComponent,
        resolve: {
            formaPagamento: FormaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'financeiroApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forma-pagamento/new',
        component: FormaPagamentoUpdateComponent,
        resolve: {
            formaPagamento: FormaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'financeiroApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'forma-pagamento/:id/edit',
        component: FormaPagamentoUpdateComponent,
        resolve: {
            formaPagamento: FormaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'financeiroApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formaPagamentoPopupRoute: Routes = [
    {
        path: 'forma-pagamento/:id/delete',
        component: FormaPagamentoDeletePopupComponent,
        resolve: {
            formaPagamento: FormaPagamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'financeiroApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
