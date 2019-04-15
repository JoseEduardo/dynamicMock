package io.github.jhipster.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.service.dto.elastic.ElasticDTO;
import io.github.jhipster.application.service.dto.elastic.ElasticLogMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessElasticService {
    private static final String URL_TEMPLATE = "http://10.2.0.170:9200/%s/_search";

    @Autowired
    private MocksService mocksService;

    public void doProcessRequest(String index, String body) throws URISyntaxException, IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Content-Type", Lists.newArrayList("application/json"));

        URI newURI = new URI(String.format(URL_TEMPLATE, index));
        RequestEntity<Object> newRequest = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, newURI);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ElasticDTO elasticDTO = new ObjectMapper().readValue(restTemplate.exchange(newRequest, String.class).getBody(), ElasticDTO.class);

        elasticDTO.getHits().getHits().stream()
            .map(hit -> hit.getSource().getLogMessage())
            .filter(ht -> !mocksService.findByRequestUrlAndMethod(ht.getUrl(), ht.getHttpMethod()).isPresent())
            .forEach(this::createMock);

    }

    private void createMock(ElasticLogMessageDTO log) {
        ObjectMapper oMapper = new ObjectMapper();

        Map<String, String> mapRequestHeaders = new HashMap<>();
        if (log.getCustomProperties().containsKey("requestHeaders")) {
            Object requestHeaders = log.getCustomProperties().get("requestHeaders");
            mapRequestHeaders = oMapper.convertValue(requestHeaders, Map.class);
        }

        Map<String, String> mapResponseHeaders = new HashMap<>();
        if (log.getCustomProperties().containsKey("responseHeaders")) {
            Object responseHeaders = log.getCustomProperties().get("responseHeaders");
            mapResponseHeaders = oMapper.convertValue(responseHeaders, Map.class);
        }

        Mocks mocks = new Mocks();
        mocks.setMethod(log.getHttpMethod());
        mocks.setRequest_body(log.getRequestBody());
        mocks.setResponse_status(log.getHttpStatus());
        mocks.setResponse_body(log.getResponseBody());

        HttpHeaders reqHeader = new HttpHeaders();
        mapRequestHeaders.forEach(x -> reqHeader.add(x.get(), x.getValue()));
        mocks.setRequestHeadersByHeader(reqHeader);

        HttpHeaders respHeader = new HttpHeaders();
        mapResponseHeaders.forEach(x -> respHeader.add(x.get(), x.getValue()));
        mocks.setResponseHeadersByHeader(respHeader);

        mocksService.save(mocks);
    }

}
