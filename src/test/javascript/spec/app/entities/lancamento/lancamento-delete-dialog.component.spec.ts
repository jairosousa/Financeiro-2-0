/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinanceiroTestModule } from '../../../test.module';
import { LancamentoDeleteDialogComponent } from 'app/entities/lancamento/lancamento-delete-dialog.component';
import { LancamentoService } from 'app/entities/lancamento/lancamento.service';

describe('Component Tests', () => {
    describe('Lancamento Management Delete Component', () => {
        let comp: LancamentoDeleteDialogComponent;
        let fixture: ComponentFixture<LancamentoDeleteDialogComponent>;
        let service: LancamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinanceiroTestModule],
                declarations: [LancamentoDeleteDialogComponent]
            })
                .overrideTemplate(LancamentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
