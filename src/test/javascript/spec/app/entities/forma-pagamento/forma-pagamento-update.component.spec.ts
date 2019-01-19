/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FinanceiroTestModule } from '../../../test.module';
import { FormaPagamentoUpdateComponent } from 'app/entities/forma-pagamento/forma-pagamento-update.component';
import { FormaPagamentoService } from 'app/entities/forma-pagamento/forma-pagamento.service';
import { FormaPagamento } from 'app/shared/model/forma-pagamento.model';

describe('Component Tests', () => {
    describe('FormaPagamento Management Update Component', () => {
        let comp: FormaPagamentoUpdateComponent;
        let fixture: ComponentFixture<FormaPagamentoUpdateComponent>;
        let service: FormaPagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinanceiroTestModule],
                declarations: [FormaPagamentoUpdateComponent]
            })
                .overrideTemplate(FormaPagamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FormaPagamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormaPagamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FormaPagamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formaPagamento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FormaPagamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formaPagamento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
