import { NgModule } from '@angular/core';

import { DynamicMockSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [DynamicMockSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [DynamicMockSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DynamicMockSharedCommonModule {}
