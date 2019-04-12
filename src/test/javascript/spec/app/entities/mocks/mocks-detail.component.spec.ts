/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DynamicMockTestModule } from '../../../test.module';
import { MocksDetailComponent } from 'app/entities/mocks/mocks-detail.component';
import { Mocks } from 'app/shared/model/mocks.model';

describe('Component Tests', () => {
    describe('Mocks Management Detail Component', () => {
        let comp: MocksDetailComponent;
        let fixture: ComponentFixture<MocksDetailComponent>;
        const route = ({ data: of({ mocks: new Mocks('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MocksDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MocksDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MocksDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mocks).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
