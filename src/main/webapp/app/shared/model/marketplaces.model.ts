export interface IMarketplaces {
    id?: string;
    marketplace?: string;
    marketplace_url?: string;
    is_fake?: boolean;
}

export class Marketplaces implements IMarketplaces {
    constructor(public id?: string, public marketplace?: string, public marketplace_url?: string, public is_fake?: boolean) {
        this.is_fake = this.is_fake || false;
    }
}
