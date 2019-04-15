package io.github.jhipster.application.web.rest;


import io.github.jhipster.application.service.BusinessElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/elastic")
public class ElasticAPI {

    @Autowired
    private BusinessElasticService businessElasticService;

    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = "Accept=*/*")
    public void receivePostRequestOnePath(@PathVariable String index, @RequestBody String body) throws URISyntaxException, IOException {
        businessElasticService.doProcessRequest(index, body);
    }
}
