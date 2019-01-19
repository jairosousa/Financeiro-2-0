import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinanceiroLancamentoModule } from './lancamento/lancamento.module';
import { FinanceiroFornecedorModule } from './fornecedor/fornecedor.module';
import { FinanceiroCategoriaModule } from './categoria/categoria.module';
import { FinanceiroEnderecoModule } from './endereco/endereco.module';
import { FinanceiroParcelaModule } from './parcela/parcela.module';
import { FinanceiroFormaPagamentoModule } from './forma-pagamento/forma-pagamento.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FinanceiroLancamentoModule,
        FinanceiroFornecedorModule,
        FinanceiroCategoriaModule,
        FinanceiroEnderecoModule,
        FinanceiroParcelaModule,
        FinanceiroFormaPagamentoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinanceiroEntityModule {}
