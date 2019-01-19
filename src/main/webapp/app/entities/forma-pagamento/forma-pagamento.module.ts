import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceiroSharedModule } from 'app/shared';
import {
    FormaPagamentoComponent,
    FormaPagamentoDetailComponent,
    FormaPagamentoUpdateComponent,
    FormaPagamentoDeletePopupComponent,
    FormaPagamentoDeleteDialogComponent,
    formaPagamentoRoute,
    formaPagamentoPopupRoute
} from './';

const ENTITY_STATES = [...formaPagamentoRoute, ...formaPagamentoPopupRoute];

@NgModule({
    imports: [FinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FormaPagamentoComponent,
        FormaPagamentoDetailComponent,
        FormaPagamentoUpdateComponent,
        FormaPagamentoDeleteDialogComponent,
        FormaPagamentoDeletePopupComponent
    ],
    entryComponents: [
        FormaPagamentoComponent,
        FormaPagamentoUpdateComponent,
        FormaPagamentoDeleteDialogComponent,
        FormaPagamentoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinanceiroFormaPagamentoModule {}
