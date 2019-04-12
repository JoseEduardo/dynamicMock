package io.github.jhipster.application.web.rest;


import io.github.jhipster.application.service.BusinessMockService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping(value = "/mock")
public class MockAPI {

    @Autowired
    private BusinessMockService businessMockService;

    @RequestMapping(value = {"/*", "/*/*", "/*/*/*", "/*/*/*/*", "/*/*/*/*/*"}, method = RequestMethod.POST, headers = "Accept=*/*")
    public ResponseEntity<Object> receivePostRequestOnePath(@RequestParam Map<String, String> reqParam, @RequestBody String body, HttpServletRequest request) throws URISyntaxException, NotFoundException, JsonProcessingException {
        return businessMockService.doProcessRequest(reqParam, body, request);
    }

}
