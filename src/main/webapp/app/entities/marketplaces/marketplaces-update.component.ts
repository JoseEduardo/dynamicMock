import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMarketplaces } from 'app/shared/model/marketplaces.model';
import { MarketplacesService } from './marketplaces.service';

@Component({
    selector: 'jhi-marketplaces-update',
    templateUrl: './marketplaces-update.component.html'
})
export class MarketplacesUpdateComponent implements OnInit {
    marketplaces: IMarketplaces;
    isSaving: boolean;

    constructor(protected marketplacesService: MarketplacesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ marketplaces }) => {
            this.marketplaces = marketplaces;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.marketplaces.id !== undefined) {
            this.subscribeToSaveResponse(this.marketplacesService.update(this.marketplaces));
        } else {
            this.subscribeToSaveResponse(this.marketplacesService.create(this.marketplaces));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarketplaces>>) {
        result.subscribe((res: HttpResponse<IMarketplaces>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
