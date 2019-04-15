package io.github.jhipster.application.service.dto.elastic;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticLogMessageDTO {

    @JsonProperty("responseBody")
    private String responseBody;

    @JsonProperty("httpMethod")
    private String httpMethod;

    @JsonProperty("httpStatus")
    private String httpStatus;

    @JsonProperty("requestBody")
    private String requestBody;

    @JsonProperty("url")
    private String url;

    private transient Map<String, Object> customProperties = new HashMap<>();

    @JsonUnwrapped
    @JsonAnyGetter
    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    @JsonAnySetter
    public void customAttributesAnySetter(String name, Object value) {
        customProperties.put(name, value);
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
