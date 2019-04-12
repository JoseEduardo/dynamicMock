import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Marketplaces } from 'app/shared/model/marketplaces.model';
import { MarketplacesService } from './marketplaces.service';
import { MarketplacesComponent } from './marketplaces.component';
import { MarketplacesDetailComponent } from './marketplaces-detail.component';
import { MarketplacesUpdateComponent } from './marketplaces-update.component';
import { MarketplacesDeletePopupComponent } from './marketplaces-delete-dialog.component';
import { IMarketplaces } from 'app/shared/model/marketplaces.model';

@Injectable({ providedIn: 'root' })
export class MarketplacesResolve implements Resolve<IMarketplaces> {
    constructor(private service: MarketplacesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMarketplaces> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Marketplaces>) => response.ok),
                map((marketplaces: HttpResponse<Marketplaces>) => marketplaces.body)
            );
        }
        return of(new Marketplaces());
    }
}

export const marketplacesRoute: Routes = [
    {
        path: '',
        component: MarketplacesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Marketplaces'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MarketplacesDetailComponent,
        resolve: {
            marketplaces: MarketplacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Marketplaces'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MarketplacesUpdateComponent,
        resolve: {
            marketplaces: MarketplacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Marketplaces'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MarketplacesUpdateComponent,
        resolve: {
            marketplaces: MarketplacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Marketplaces'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketplacesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MarketplacesDeletePopupComponent,
        resolve: {
            marketplaces: MarketplacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Marketplaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
