package io.github.jhipster.application.service.dto.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticSourceDTO {

    @JsonProperty("logMessage")
    private ElasticLogMessageDTO logMessage;

    public ElasticLogMessageDTO getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(ElasticLogMessageDTO logMessage) {
        this.logMessage = logMessage;
    }
}
