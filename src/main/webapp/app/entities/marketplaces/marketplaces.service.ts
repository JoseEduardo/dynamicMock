import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMarketplaces } from 'app/shared/model/marketplaces.model';

type EntityResponseType = HttpResponse<IMarketplaces>;
type EntityArrayResponseType = HttpResponse<IMarketplaces[]>;

@Injectable({ providedIn: 'root' })
export class MarketplacesService {
    public resourceUrl = SERVER_API_URL + 'api/marketplaces';

    constructor(protected http: HttpClient) {}

    create(marketplaces: IMarketplaces): Observable<EntityResponseType> {
        return this.http.post<IMarketplaces>(this.resourceUrl, marketplaces, { observe: 'response' });
    }

    update(marketplaces: IMarketplaces): Observable<EntityResponseType> {
        return this.http.put<IMarketplaces>(this.resourceUrl, marketplaces, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IMarketplaces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMarketplaces[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
