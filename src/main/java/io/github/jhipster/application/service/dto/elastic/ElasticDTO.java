package io.github.jhipster.application.service.dto.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticDTO {

    @JsonProperty("hits")
    private SingleHitsDTO hits;

    public SingleHitsDTO getHits() {
        return hits;
    }

    public void setHits(SingleHitsDTO hits) {
        this.hits = hits;
    }
}
