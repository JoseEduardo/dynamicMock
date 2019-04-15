package io.github.jhipster.application.service.dto.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleHitsDTO {

    @JsonProperty("hits")
    private List<HitsDetailDTO> hits;

    public List<HitsDetailDTO> getHits() {
        return hits;
    }

    public void setHits(List<HitsDetailDTO> hits) {
        this.hits = hits;
    }
}
