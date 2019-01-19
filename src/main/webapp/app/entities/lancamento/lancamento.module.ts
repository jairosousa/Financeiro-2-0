import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceiroSharedModule } from 'app/shared';
import {
    LancamentoComponent,
    LancamentoDetailComponent,
    LancamentoUpdateComponent,
    LancamentoDeletePopupComponent,
    LancamentoDeleteDialogComponent,
    lancamentoRoute,
    lancamentoPopupRoute
} from './';

const ENTITY_STATES = [...lancamentoRoute, ...lancamentoPopupRoute];

@NgModule({
    imports: [FinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LancamentoComponent,
        LancamentoDetailComponent,
        LancamentoUpdateComponent,
        LancamentoDeleteDialogComponent,
        LancamentoDeletePopupComponent
    ],
    entryComponents: [LancamentoComponent, LancamentoUpdateComponent, LancamentoDeleteDialogComponent, LancamentoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinanceiroLancamentoModule {}
