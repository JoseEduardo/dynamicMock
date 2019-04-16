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
    reqHeaderNew = {};
    resHeaderNew = {};

    constructor(protected mocksService: MocksService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mocks }) => {
            this.mocks = mocks;
        });
    }

    removeReqHeader(header) {
        for (var i = 0; i < this.mocks.request_headers.length; i++) {
            if(this.mocks.request_headers[i].key == header.key) {
                this.mocks.request_headers.splice(i,1);
                break;
            }
        }
    }

    saveReqHeader(header) {
        for (var i = 0; i < this.mocks.request_headers.length; i++) {
            if(this.mocks.request_headers[i].key == header.key) {
                this.mocks.request_headers[i].key   = header.key;
                this.mocks.request_headers[i].value = header.value;
                break;
            }
        }
    }

    addReqHeader() {
        if(this.mocks.request_headers == undefined) {
            this.mocks.request_headers = [];
        }
        this.mocks.request_headers.push(this.reqHeaderNew);
        this.reqHeaderNew = {};
    }

    removeResHeader(header) {
        for (var i = 0; i < this.mocks.response_headers.length; i++) {
            if(this.mocks.response_headers[i].key == header.key) {
                this.mocks.response_headers.splice(i,1);
                break;
            }
        }
    }

    saveResHeader(header) {
        for (var i = 0; i < this.mocks.response_headers.length; i++) {
            if(this.mocks.response_headers[i].key == header.key) {
                this.mocks.response_headers[i].key   = header.key;
                this.mocks.response_headers[i].value = header.value;
                break;
            }
        }
    }

    addResHeader() {
        if(this.mocks.response_headers == undefined) {
            this.mocks.response_headers = [];
        }
        this.mocks.response_headers.push(this.resHeaderNew);
        this.resHeaderNew = {};
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
