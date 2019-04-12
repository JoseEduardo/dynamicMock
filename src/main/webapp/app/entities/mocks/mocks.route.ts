import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mocks } from 'app/shared/model/mocks.model';
import { MocksService } from './mocks.service';
import { MocksComponent } from './mocks.component';
import { MocksDetailComponent } from './mocks-detail.component';
import { MocksUpdateComponent } from './mocks-update.component';
import { MocksDeletePopupComponent } from './mocks-delete-dialog.component';
import { IMocks } from 'app/shared/model/mocks.model';

@Injectable({ providedIn: 'root' })
export class MocksResolve implements Resolve<IMocks> {
    constructor(private service: MocksService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMocks> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Mocks>) => response.ok),
                map((mocks: HttpResponse<Mocks>) => mocks.body)
            );
        }
        return of(new Mocks());
    }
}

export const mocksRoute: Routes = [
    {
        path: '',
        component: MocksComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mocks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MocksDetailComponent,
        resolve: {
            mocks: MocksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mocks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MocksUpdateComponent,
        resolve: {
            mocks: MocksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mocks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MocksUpdateComponent,
        resolve: {
            mocks: MocksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mocks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mocksPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MocksDeletePopupComponent,
        resolve: {
            mocks: MocksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mocks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
