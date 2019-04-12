import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DynamicMockSharedModule } from 'app/shared';
import {
    MocksComponent,
    MocksDetailComponent,
    MocksUpdateComponent,
    MocksDeletePopupComponent,
    MocksDeleteDialogComponent,
    mocksRoute,
    mocksPopupRoute
} from './';

const ENTITY_STATES = [...mocksRoute, ...mocksPopupRoute];

@NgModule({
    imports: [DynamicMockSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MocksComponent, MocksDetailComponent, MocksUpdateComponent, MocksDeleteDialogComponent, MocksDeletePopupComponent],
    entryComponents: [MocksComponent, MocksUpdateComponent, MocksDeleteDialogComponent, MocksDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DynamicMockMocksModule {}
