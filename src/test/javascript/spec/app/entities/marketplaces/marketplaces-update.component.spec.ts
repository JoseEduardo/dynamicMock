/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DynamicMockTestModule } from '../../../test.module';
import { MarketplacesUpdateComponent } from 'app/entities/marketplaces/marketplaces-update.component';
import { MarketplacesService } from 'app/entities/marketplaces/marketplaces.service';
import { Marketplaces } from 'app/shared/model/marketplaces.model';

describe('Component Tests', () => {
    describe('Marketplaces Management Update Component', () => {
        let comp: MarketplacesUpdateComponent;
        let fixture: ComponentFixture<MarketplacesUpdateComponent>;
        let service: MarketplacesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MarketplacesUpdateComponent]
            })
                .overrideTemplate(MarketplacesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarketplacesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketplacesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Marketplaces('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marketplaces = entity;
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
                    const entity = new Marketplaces();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marketplaces = entity;
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
