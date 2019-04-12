/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DynamicMockTestModule } from '../../../test.module';
import { MocksDeleteDialogComponent } from 'app/entities/mocks/mocks-delete-dialog.component';
import { MocksService } from 'app/entities/mocks/mocks.service';

describe('Component Tests', () => {
    describe('Mocks Management Delete Component', () => {
        let comp: MocksDeleteDialogComponent;
        let fixture: ComponentFixture<MocksDeleteDialogComponent>;
        let service: MocksService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MocksDeleteDialogComponent]
            })
                .overrideTemplate(MocksDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MocksDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MocksService);
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
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
