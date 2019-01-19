import { IParcela } from 'app/shared/model//parcela.model';

export const enum Moeda {
    DINHEIRO = 'DINHEIRO',
    CARTAO = 'CARTAO'
}

export interface IFormaPagamento {
    id?: number;
    moeda?: Moeda;
    descricao?: string;
    observacao?: string;
    parcelas?: IParcela[];
}

export class FormaPagamento implements IFormaPagamento {
    constructor(
        public id?: number,
        public moeda?: Moeda,
        public descricao?: string,
        public observacao?: string,
        public parcelas?: IParcela[]
    ) {}
}
