/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DynamicMockTestModule } from '../../../test.module';
import { MocksUpdateComponent } from 'app/entities/mocks/mocks-update.component';
import { MocksService } from 'app/entities/mocks/mocks.service';
import { Mocks } from 'app/shared/model/mocks.model';

describe('Component Tests', () => {
    describe('Mocks Management Update Component', () => {
        let comp: MocksUpdateComponent;
        let fixture: ComponentFixture<MocksUpdateComponent>;
        let service: MocksService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DynamicMockTestModule],
                declarations: [MocksUpdateComponent]
            })
                .overrideTemplate(MocksUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MocksUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MocksService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Mocks('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mocks = entity;
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
                    const entity = new Mocks();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mocks = entity;
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
