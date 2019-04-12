import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarketplaces } from 'app/shared/model/marketplaces.model';
import { MarketplacesService } from './marketplaces.service';

@Component({
    selector: 'jhi-marketplaces-delete-dialog',
    templateUrl: './marketplaces-delete-dialog.component.html'
})
export class MarketplacesDeleteDialogComponent {
    marketplaces: IMarketplaces;

    constructor(
        protected marketplacesService: MarketplacesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.marketplacesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'marketplacesListModification',
                content: 'Deleted an marketplaces'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marketplaces-delete-popup',
    template: ''
})
export class MarketplacesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marketplaces }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MarketplacesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.marketplaces = marketplaces;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/marketplaces', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/marketplaces', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
