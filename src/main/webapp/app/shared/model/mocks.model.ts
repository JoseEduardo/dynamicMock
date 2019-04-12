export interface IMocks {
    id?: string;
    method?: string;
    request_url?: string;
    request_headers?: [];
    request_body?: string;
    response_headers?: [];
    response_body?: string;
    response_status?: string;
}

export class Mocks implements IMocks {
    constructor(
        public id?: string,
        public method?: string,
        public request_url?: string,
        public request_headers?: [],
        public request_body?: string,
        public response_headers?: [],
        public response_body?: string,
        public response_status?: string
    ) {}
}
