import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DynamicMockSharedModule } from 'app/shared';
import {
    MarketplacesComponent,
    MarketplacesDetailComponent,
    MarketplacesUpdateComponent,
    MarketplacesDeletePopupComponent,
    MarketplacesDeleteDialogComponent,
    marketplacesRoute,
    marketplacesPopupRoute
} from './';

const ENTITY_STATES = [...marketplacesRoute, ...marketplacesPopupRoute];

@NgModule({
    imports: [DynamicMockSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MarketplacesComponent,
        MarketplacesDetailComponent,
        MarketplacesUpdateComponent,
        MarketplacesDeleteDialogComponent,
        MarketplacesDeletePopupComponent
    ],
    entryComponents: [
        MarketplacesComponent,
        MarketplacesUpdateComponent,
        MarketplacesDeleteDialogComponent,
        MarketplacesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DynamicMockMarketplacesModule {}
