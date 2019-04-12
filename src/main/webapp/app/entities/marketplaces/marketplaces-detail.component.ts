import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarketplaces } from 'app/shared/model/marketplaces.model';

@Component({
    selector: 'jhi-marketplaces-detail',
    templateUrl: './marketplaces-detail.component.html'
})
export class MarketplacesDetailComponent implements OnInit {
    marketplaces: IMarketplaces;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marketplaces }) => {
            this.marketplaces = marketplaces;
        });
    }

    previousState() {
        window.history.back();
    }
}
