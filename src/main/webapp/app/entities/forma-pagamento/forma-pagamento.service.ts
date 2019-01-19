import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFormaPagamento } from 'app/shared/model/forma-pagamento.model';

type EntityResponseType = HttpResponse<IFormaPagamento>;
type EntityArrayResponseType = HttpResponse<IFormaPagamento[]>;

@Injectable({ providedIn: 'root' })
export class FormaPagamentoService {
    public resourceUrl = SERVER_API_URL + 'api/forma-pagamentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/forma-pagamentos';

    constructor(protected http: HttpClient) {}

    create(formaPagamento: IFormaPagamento): Observable<EntityResponseType> {
        return this.http.post<IFormaPagamento>(this.resourceUrl, formaPagamento, { observe: 'response' });
    }

    update(formaPagamento: IFormaPagamento): Observable<EntityResponseType> {
        return this.http.put<IFormaPagamento>(this.resourceUrl, formaPagamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFormaPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFormaPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFormaPagamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
