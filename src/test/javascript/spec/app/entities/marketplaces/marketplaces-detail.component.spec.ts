/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DynamicMockTestModule } from '../../../test.module';
import { MarketplacesDetailComponent } from 'app/entities/marketplaces/marketplaces-detail.component';
import { Marketplaces } from 'app/shared/model/marketplaces.model';

describe('Component Tests', () => {
    describe('Marketplaces Management Detail Component', () => {
        let comp: MarketplacesDetailComponent;
        let fixture: ComponentFixture<MarketplacesDetailComponent>;
        const route = ({ data: of({ marketplaces: new Marketplaces('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MarketplacesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MarketplacesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MarketplacesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.marketplaces).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
