export interface IMarketplaces {
    id?: string;
    marketplace?: string;
    marketplace_url?: string;
}

export class Marketplaces implements IMarketplaces {
    constructor(public id?: string, public marketplace?: string, public marketplace_url?: string) {}
}
