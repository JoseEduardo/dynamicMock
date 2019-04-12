/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DynamicMockTestModule } from '../../../test.module';
import { MarketplacesDeleteDialogComponent } from 'app/entities/marketplaces/marketplaces-delete-dialog.component';
import { MarketplacesService } from 'app/entities/marketplaces/marketplaces.service';

describe('Component Tests', () => {
    describe('Marketplaces Management Delete Component', () => {
        let comp: MarketplacesDeleteDialogComponent;
        let fixture: ComponentFixture<MarketplacesDeleteDialogComponent>;
        let service: MarketplacesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MarketplacesDeleteDialogComponent]
            })
                .overrideTemplate(MarketplacesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MarketplacesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketplacesService);
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
