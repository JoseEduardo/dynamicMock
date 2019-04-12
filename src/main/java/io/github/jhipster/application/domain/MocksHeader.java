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
public class MocksHeader implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field("key")
    private String key;

    @Field("value")
    private String value;

    public MocksHeader() {
    }

    public MocksHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MocksHeader that = (MocksHeader) o;
        return Objects.equals(key, that.key) &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "MocksHeader{" +
            "key='" + key + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
