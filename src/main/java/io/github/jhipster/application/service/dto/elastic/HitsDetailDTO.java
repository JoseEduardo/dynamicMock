package io.github.jhipster.application.service.dto.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HitsDetailDTO {

    @JsonProperty("_source")
    private ElasticSourceDTO source;

    public ElasticSourceDTO getSource() {
        return source;
    }

    public void setSource(ElasticSourceDTO source) {
        this.source = source;
    }
}
