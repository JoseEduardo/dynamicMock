package io.github.jhipster.application.domain;

import java.net.URI;

public class MarketplaceCallDTO {

    private String relativeUrl;
    private URI newURI;
    private boolean isFake = false;
    private String marketplace;

    public MarketplaceCallDTO() {
    }

    public MarketplaceCallDTO(URI newURI, String relativeUrl, boolean isFake, String marketplace) {
        this.relativeUrl = relativeUrl;
        this.newURI = newURI;
        this.isFake = isFake;
        this.marketplace = marketplace;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public URI getNewURI() {
        return newURI;
    }

    public void setNewURI(URI newURI) {
        this.newURI = newURI;
    }

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }
}
