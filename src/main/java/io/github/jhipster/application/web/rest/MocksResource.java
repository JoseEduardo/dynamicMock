package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.repository.MocksRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mocks.
 */
@RestController
@RequestMapping("/api")
public class MocksResource {

    private final Logger log = LoggerFactory.getLogger(MocksResource.class);

    private static final String ENTITY_NAME = "mocks";

    private final MocksRepository mocksRepository;

    public MocksResource(MocksRepository mocksRepository) {
        this.mocksRepository = mocksRepository;
    }

    /**
     * POST  /mocks : Create a new mocks.
     *
     * @param mocks the mocks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mocks, or with status 400 (Bad Request) if the mocks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mocks")
    public ResponseEntity<Mocks> createMocks(@RequestBody Mocks mocks) throws URISyntaxException {
        log.debug("REST request to save Mocks : {}", mocks);
        if (mocks.getId() != null) {
            throw new BadRequestAlertException("A new mocks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mocks result = mocksRepository.save(mocks);
        return ResponseEntity.created(new URI("/api/mocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mocks : Updates an existing mocks.
     *
     * @param mocks the mocks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mocks,
     * or with status 400 (Bad Request) if the mocks is not valid,
     * or with status 500 (Internal Server Error) if the mocks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping(value = "/mocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mocks> updateMocks(@RequestBody Mocks mocks) throws URISyntaxException {
        log.debug("REST request to update Mocks : {}", mocks);
        if (mocks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mocks result = mocksRepository.save(mocks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mocks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mocks : get all the mocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mocks in body
     */
    @GetMapping("/mocks")
    public List<Mocks> getAllMocks() {
        log.debug("REST request to get all Mocks");
        return mocksRepository.findAll();
    }

    /**
     * GET  /mocks/:id : get the "id" mocks.
     *
     * @param id the id of the mocks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mocks, or with status 404 (Not Found)
     */
    @GetMapping("/mocks/{id}")
    public ResponseEntity<Mocks> getMocks(@PathVariable String id) {
        log.debug("REST request to get Mocks : {}", id);
        Optional<Mocks> mocks = mocksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mocks);
    }

    /**
     * DELETE  /mocks/:id : delete the "id" mocks.
     *
     * @param id the id of the mocks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mocks/{id}")
    public ResponseEntity<Void> deleteMocks(@PathVariable String id) {
        log.debug("REST request to delete Mocks : {}", id);
        mocksRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
