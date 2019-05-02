import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'marketplaces',
                loadChildren: './marketplaces/marketplaces.module#DynamicMockMarketplacesModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'mocks',
                loadChildren: './mocks/mocks.module#DynamicMockMocksModule'
            },
            {
                path: 'marketplaces',
                loadChildren: './marketplaces/marketplaces.module#DynamicMockMarketplacesModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DynamicMockEntityModule {}
