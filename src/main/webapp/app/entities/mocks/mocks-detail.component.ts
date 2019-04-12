import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMocks } from 'app/shared/model/mocks.model';

@Component({
    selector: 'jhi-mocks-detail',
    templateUrl: './mocks-detail.component.html'
})
export class MocksDetailComponent implements OnInit {
    mocks: IMocks;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mocks }) => {
            this.mocks = mocks;
        });
    }

    previousState() {
        window.history.back();
    }
}
