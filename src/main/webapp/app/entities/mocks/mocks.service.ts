import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMocks } from 'app/shared/model/mocks.model';

type EntityResponseType = HttpResponse<IMocks>;
type EntityArrayResponseType = HttpResponse<IMocks[]>;

@Injectable({ providedIn: 'root' })
export class MocksService {
    public resourceUrl = SERVER_API_URL + 'api/mocks';

    constructor(protected http: HttpClient) {}

    create(mocks: IMocks): Observable<EntityResponseType> {
        console.log("BATATA");
        var requestHeaders = [];
        for (const header in mocks.request_headers) {
            //requestHeaders.push()
            console.log(header);
        }

        return this.http.post<IMocks>(this.resourceUrl, mocks, { observe: 'response' });
    }

    update(mocks: IMocks): Observable<EntityResponseType> {
        console.log("BATATA");
        var requestHeaders = [];
        for (const header in mocks.request_headers) {
            //requestHeaders.push()
            console.log(header);
        }

        return this.http.put<IMocks>(this.resourceUrl, mocks, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IMocks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMocks[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
