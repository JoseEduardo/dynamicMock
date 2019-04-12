import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMocks } from 'app/shared/model/mocks.model';
import { MocksService } from './mocks.service';

@Component({
    selector: 'jhi-mocks-update',
    templateUrl: './mocks-update.component.html'
})
export class MocksUpdateComponent implements OnInit {
    mocks: IMocks;
    isSaving: boolean;

    constructor(protected mocksService: MocksService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mocks }) => {
            this.mocks = mocks;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mocks.id !== undefined) {
            this.subscribeToSaveResponse(this.mocksService.update(this.mocks));
        } else {
            this.subscribeToSaveResponse(this.mocksService.create(this.mocks));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMocks>>) {
        result.subscribe((res: HttpResponse<IMocks>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
