/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DynamicMockTestModule } from '../../../test.module';
import { MarketplacesComponent } from 'app/entities/marketplaces/marketplaces.component';
import { MarketplacesService } from 'app/entities/marketplaces/marketplaces.service';
import { Marketplaces } from 'app/shared/model/marketplaces.model';

describe('Component Tests', () => {
    describe('Marketplaces Management Component', () => {
        let comp: MarketplacesComponent;
        let fixture: ComponentFixture<MarketplacesComponent>;
        let service: MarketplacesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MarketplacesComponent],
                providers: []
            })
                .overrideTemplate(MarketplacesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarketplacesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketplacesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Marketplaces('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.marketplaces[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
