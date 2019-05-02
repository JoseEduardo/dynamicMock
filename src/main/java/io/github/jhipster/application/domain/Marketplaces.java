package io.github.jhipster.application.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Marketplaces.
 */
@Document(collection = "marketplaces")
public class Marketplaces implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("marketplace")
    private String marketplace;

    @Field("marketplace_url")
    private String marketplace_url;

    @Field("is_fake")
    private Boolean is_fake;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public Marketplaces marketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public String getMarketplace_url() {
        return marketplace_url;
    }

    public Marketplaces marketplace_url(String marketplace_url) {
        this.marketplace_url = marketplace_url;
        return this;
    }

    public void setMarketplace_url(String marketplace_url) {
        this.marketplace_url = marketplace_url;
    }

    public Boolean isIs_fake() {
        return is_fake;
    }

    public Marketplaces is_fake(Boolean is_fake) {
        this.is_fake = is_fake;
        return this;
    }

    public void setIs_fake(Boolean is_fake) {
        this.is_fake = is_fake;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Marketplaces marketplaces = (Marketplaces) o;
        if (marketplaces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketplaces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Marketplaces{" +
            "id=" + getId() +
            ", marketplace='" + getMarketplace() + "'" +
            ", marketplace_url='" + getMarketplace_url() + "'" +
            ", is_fake='" + isIs_fake() + "'" +
            "}";
    }
}
