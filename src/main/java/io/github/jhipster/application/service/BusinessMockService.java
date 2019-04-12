package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Marketplaces;
import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.domain.MocksHeader;
import javassist.NotFoundException;
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
    private static final String URI_FINAL = "%s%s?%s";

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
        URI newURI = findNewURI(request, reqParam);

        Optional<Mocks> optMockedCall = mocksService.findByRequestUrlAndMethod(newURI.toString(), request.getMethod());
        if (optMockedCall.isPresent()) {
            Mocks mockedCall = optMockedCall.get();

            List<MocksHeader> responseHeaders = mockedCall.getResponse_headers();
            HttpHeaders respHeader = new HttpHeaders();
            responseHeaders.forEach(x -> respHeader.add(x.getKey(), x.getValue()));

            HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(mockedCall.getResponse_status()));
            return new ResponseEntity(mockedCall.getResponse_body(), respHeader, httpStatus);
        }

        RequestEntity<Object> newRequest = new RequestEntity<>(body, headers, HttpMethod.valueOf(request.getMethod()), newURI);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<Object> retObject = restTemplate.exchange(newRequest, Object.class);

        Mocks mocks = new Mocks();
        mocks.setMethod(request.getMethod());
        mocks.setRequest_body(body);
        mocks.setRequest_url(newURI.toString());
        mocks.setRequestHeadersByHeader(headers);
        mocks.setResponse_status(retObject.getStatusCode().toString());
        mocks.setResponse_body(isNull(retObject.getBody()) ? "" : new ObjectMapper().writeValueAsString(retObject.getBody()));
        mocks.setResponseHeadersByHeader(retObject.getHeaders());

        mocksService.save(mocks);
        return retObject;
    }

    private URI findNewURI(HttpServletRequest request, Map<String, String> reqParam) throws URISyntaxException, NotFoundException {
        String relPath = request.getRequestURI();
        String relPathWtotMock = relPath.replace("/mock", "");
        String marketplaceName = relPathWtotMock.replace("/", "");

        String marketplaceUrl = marketplacesService.findAll().stream()
            .filter(mkt -> marketplaceName.contains(mkt.getMarketplace()))
            .map(Marketplaces::getMarketplace_url)
            .findFirst()
            .orElseThrow(() -> new NotFoundException(String.format("Marketplace %s não configurado", marketplaceName)));

        String requestParam = reqParam.entrySet().stream()
            .map(header -> header.getKey().concat("=").concat(header.getValue()))
            .collect(Collectors.joining("&"));

        String resultUrl = String.format(URI_FINAL, marketplaceUrl, relPathWtotMock, requestParam);
        return new URI(resultUrl);
    }

}
