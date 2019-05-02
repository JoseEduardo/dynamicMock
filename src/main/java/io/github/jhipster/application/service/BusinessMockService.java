package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.MarketplaceCallDTO;
import io.github.jhipster.application.domain.Marketplaces;
import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.domain.MocksHeader;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class BusinessMockService {
    private final Logger log = LoggerFactory.getLogger(BusinessMockService.class);

    private static final String URI_FINAL = "%s%s?%s";
    private static final String URI_PART_FINAL = "%s?%s";

    @Autowired
    private MocksService mocksService;
    @Autowired
    private MarketplacesService marketplacesService;

    public ResponseEntity<Object> doProcessRequest(Map<String, String> reqParam, String body, HttpServletRequest request) throws URISyntaxException, NotFoundException, JsonProcessingException {
        final HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host")) {
                headers.add(headerName, request.getHeader(headerName));
            }
        }
        MarketplaceCallDTO mktDto = findNewURI(request, reqParam);

        log.info("Processando chamada {}", mktDto.getRelativeUrl());
        Optional<Mocks> optMockedCall = mocksService.findByRequestUrlAndMethod(mktDto.getRelativeUrl(), request.getMethod());
        if (optMockedCall.isPresent()) {
            log.info("Retornando MOCK chamada {}", mktDto.getRelativeUrl());
            return returnByMock(mktDto.getRelativeUrl(), optMockedCall.get());
        }

        ResponseEntity<Object> retObject;
        if (mktDto.isFake()) {
            log.info("Retornando FAKE_MOCK chamada {}", mktDto.getRelativeUrl());
            retObject = returnFakeMock();
        } else {
            log.info("Retornando REAL chamada {}", mktDto.getRelativeUrl());
            retObject = returnByMarketplaceCall(body, request, headers, mktDto.getNewURI());
        }

        Mocks mocks = new Mocks();
        mocks.setMethod(request.getMethod());
        mocks.setRequest_body(body);
        mocks.setRequest_url(mktDto.getRelativeUrl());
        mocks.setRequestHeadersByHeader(headers);
        mocks.setResponse_status(retObject.getStatusCode().toString());
        mocks.setResponse_body(isNull(retObject.getBody()) ? "" : new ObjectMapper().writeValueAsString(retObject.getBody()));
        mocks.setResponseHeadersByHeader(retObject.getHeaders());
        mocks.setMarketplace(mktDto.getMarketplace());

        mocksService.save(mocks);
        log.info("Retornando Conteudo chamada {}", mktDto.getRelativeUrl());
        return retObject;
    }

    private ResponseEntity<Object> returnByMarketplaceCall(String body, HttpServletRequest request, HttpHeaders headers, URI newURI) {
        RequestEntity<Object> newRequest = new RequestEntity<>(body, headers, HttpMethod.valueOf(request.getMethod()), newURI);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.exchange(newRequest, Object.class);
    }

    private ResponseEntity<Object> returnFakeMock() {
        HttpHeaders respHeader = new HttpHeaders();
        respHeader.add("Content-Type", "application/json");
        String defaultBodyResp = "{\"status\": \"ok\"}";
        return new ResponseEntity(defaultBodyResp, respHeader, HttpStatus.OK);
    }

    private ResponseEntity<Object> returnByMock(String relativeUrl, Mocks mockedCall) {
        List<MocksHeader> responseHeaders = mockedCall.getResponse_headers();
        HttpHeaders respHeader = new HttpHeaders();
        responseHeaders.forEach(x -> respHeader.add(x.getKey(), x.getValue()));

        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(mockedCall.getResponse_status()));
        return new ResponseEntity(mockedCall.getResponse_body(), respHeader, httpStatus);
    }

    private MarketplaceCallDTO findNewURI(HttpServletRequest request, Map<String, String> reqParam) throws URISyntaxException, NotFoundException {
        String relPath = request.getRequestURI();
        String relPathWtotMock = relPath.replace("/mock", "");
        String marketplaceName = relPathWtotMock.replace("/", "");

        Marketplaces marketplaces = marketplacesService.findAll().stream()
            .filter(mkt -> marketplaceName.contains(mkt.getMarketplace()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(String.format("Marketplace %s não configurado", marketplaceName)));
        String marketplaceUrl = marketplaces.getMarketplace_url();

        String requestParam = reqParam.entrySet().stream()
            .map(header -> header.getKey().concat("=").concat(header.getValue()))
            .collect(Collectors.joining("&"));

        String finalPath = relPathWtotMock.replace("/" + marketplaces.getMarketplace(), "");
        String resultUrl = String.format(URI_FINAL, marketplaceUrl, finalPath, requestParam);

        String resultPartUrl = String.format(URI_PART_FINAL, relPathWtotMock, requestParam);
        return new MarketplaceCallDTO(new URI(resultUrl), resultPartUrl, marketplaces.isIs_fake(), marketplaces.getMarketplace());
    }

}
