package io.github.jhipster.application.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A Mocks.
 */
@Document(collection = "mocks")
public class Mocks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("method")
    private String method;

    @Field("request_url")
    private String request_url;

    @Field("request_headers")
    private Map<String, String> request_headers;

    @Field("request_body")
    private String request_body;

    @Field("response_headers")
    private Map<String, String> response_headers;

    @Field("response_body")
    private String response_body;

    @Field("response_status")
    private String response_status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public Mocks method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequest_url() {
        return request_url;
    }

    public Mocks request_url(String request_url) {
        this.request_url = request_url;
        return this;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }

    public Map<String, String> getRequest_headers() {
        return request_headers;
    }

    public Mocks request_headers(Map<String, String> request_headers) {
        this.request_headers = request_headers;
        return this;
    }

    public void setRequest_headers(Map<String, String> request_headers) {
        this.request_headers = request_headers;
    }

    public void setRequest_headers(HttpHeaders headers) {
        Map<String, String> mpHeaders = new HashMap<>();
        headers.forEach((key, value) -> mpHeaders.put(key, value.toString()));
        this.request_headers = mpHeaders;
    }

    public String getRequest_body() {
        return request_body;
    }

    public Mocks request_body(String request_body) {
        this.request_body = request_body;
        return this;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public Map<String, String> getResponse_headers() {
        return response_headers;
    }

    public Mocks response_headers(Map<String, String> response_headers) {
        this.response_headers = response_headers;
        return this;
    }

    public void setResponse_headers(Map<String, String> response_headers) {
        this.response_headers = response_headers;
    }

    public void setResponse_headers(HttpHeaders headers) {
        Map<String, String> mpHeaders = new HashMap<>();
        headers.forEach((key, value) -> mpHeaders.put(key, value.toString()));
        this.response_headers = mpHeaders;
    }


    public String getResponse_body() {
        return response_body;
    }

    public Mocks response_body(String response_body) {
        this.response_body = response_body;
        return this;
    }

    public void setResponse_body(String response_body) {
        this.response_body = response_body;
    }

    public String getResponse_status() {
        return response_status;
    }

    public Mocks response_status(String response_status) {
        this.response_status = response_status;
        return this;
    }

    public void setResponse_status(String response_status) {
        this.response_status = response_status;
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
        Mocks mocks = (Mocks) o;
        if (mocks.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mocks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mocks{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            ", request_url='" + getRequest_url() + "'" +
            ", request_headers='" + getRequest_headers() + "'" +
            ", request_body='" + getRequest_body() + "'" +
            ", response_headers='" + getResponse_headers() + "'" +
            ", response_body='" + getResponse_body() + "'" +
            ", response_status='" + getResponse_status() + "'" +
            "}";
    }
}
