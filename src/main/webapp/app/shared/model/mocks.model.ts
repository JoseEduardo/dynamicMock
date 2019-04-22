export class Headers {
    key?: string;
    value?: string;
}

export interface IMocks {
    id?: string;
    method?: string;
    request_url?: string;
    request_headers?: [Headers];
    request_body?: string;
    response_headers?: [Headers];
    response_body?: string;
    response_status?: string;
    marketplace?: string;
}

export class Mocks implements IMocks {
    constructor(
        public id?: string,
        public method?: string,
        public request_url?: string,
        public request_headers?: [Headers],
        public request_body?: string,
        public response_headers?: [Headers],
        public response_body?: string,
        public response_status?: string,
        public marketplace?: string
    ) {}
}
