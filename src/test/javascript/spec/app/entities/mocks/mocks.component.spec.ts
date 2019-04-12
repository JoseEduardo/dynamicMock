/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DynamicMockTestModule } from '../../../test.module';
import { MocksComponent } from 'app/entities/mocks/mocks.component';
import { MocksService } from 'app/entities/mocks/mocks.service';
import { Mocks } from 'app/shared/model/mocks.model';

describe('Component Tests', () => {
    describe('Mocks Management Component', () => {
        let comp: MocksComponent;
        let fixture: ComponentFixture<MocksComponent>;
        let service: MocksService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MocksComponent],
                providers: []
            })
                .overrideTemplate(MocksComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MocksComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MocksService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Mocks('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mocks[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
