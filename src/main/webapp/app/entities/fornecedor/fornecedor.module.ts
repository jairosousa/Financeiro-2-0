import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceiroSharedModule } from 'app/shared';
import {
    FornecedorComponent,
    FornecedorDetailComponent,
    FornecedorUpdateComponent,
    FornecedorDeletePopupComponent,
    FornecedorDeleteDialogComponent,
    fornecedorRoute,
    fornecedorPopupRoute
} from './';

const ENTITY_STATES = [...fornecedorRoute, ...fornecedorPopupRoute];

@NgModule({
    imports: [FinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FornecedorComponent,
        FornecedorDetailComponent,
        FornecedorUpdateComponent,
        FornecedorDeleteDialogComponent,
        FornecedorDeletePopupComponent
    ],
    entryComponents: [FornecedorComponent, FornecedorUpdateComponent, FornecedorDeleteDialogComponent, FornecedorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinanceiroFornecedorModule {}
