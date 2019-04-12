import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMocks } from 'app/shared/model/mocks.model';
import { MocksService } from './mocks.service';

@Component({
    selector: 'jhi-mocks-delete-dialog',
    templateUrl: './mocks-delete-dialog.component.html'
})
export class MocksDeleteDialogComponent {
    mocks: IMocks;

    constructor(protected mocksService: MocksService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.mocksService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mocksListModification',
                content: 'Deleted an mocks'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mocks-delete-popup',
    template: ''
})
export class MocksDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mocks }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MocksDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mocks = mocks;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mocks', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mocks', { outlets: { popup: null } }]);
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
