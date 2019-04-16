import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import {Headers, IMocks} from 'app/shared/model/mocks.model';
import { MocksService } from './mocks.service';

@Component({
    selector: 'jhi-mocks-update',
    templateUrl: './mocks-update.component.html'
})
export class MocksUpdateComponent implements OnInit {
    mocks: IMocks;
    isSaving: boolean;
    reqHeaderNew = new Headers();
    resHeaderNew = new Headers();

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
        let headers = new Headers();
        headers.key = this.reqHeaderNew['key'];
        headers.value = this.reqHeaderNew['value'];

        if(this.mocks.request_headers == undefined) {
            this.mocks.request_headers = [headers];
        }else {
            this.mocks.request_headers.push(headers);
        }
        this.reqHeaderNew['key'] = "";
        this.reqHeaderNew['value'] = "";
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
        let headers = new Headers();
        headers.key = this.resHeaderNew['key'];
        headers.value = this.resHeaderNew['value'];
        if(this.mocks.response_headers == undefined) {
            this.mocks.response_headers = [headers];
        }else{
            this.mocks.response_headers.push(headers);
        }
        this.resHeaderNew['key'] = "";
        this.resHeaderNew['value'] = "";
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
