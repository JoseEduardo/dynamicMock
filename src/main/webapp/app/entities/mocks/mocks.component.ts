import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMocks } from 'app/shared/model/mocks.model';
import { AccountService } from 'app/core';
import { MocksService } from './mocks.service';

@Component({
    selector: 'jhi-mocks',
    templateUrl: './mocks.component.html'
})
export class MocksComponent implements OnInit, OnDestroy {
    mocks: IMocks[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected mocksService: MocksService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.mocksService
            .query()
            .pipe(
                filter((res: HttpResponse<IMocks[]>) => res.ok),
                map((res: HttpResponse<IMocks[]>) => res.body)
            )
            .subscribe(
                (res: IMocks[]) => {
                    this.mocks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMocks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMocks) {
        return item.id;
    }

    registerChangeInMocks() {
        this.eventSubscriber = this.eventManager.subscribe('mocksListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
