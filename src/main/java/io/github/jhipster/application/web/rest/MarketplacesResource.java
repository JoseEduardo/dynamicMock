package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Marketplaces;
import io.github.jhipster.application.service.MarketplacesService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Marketplaces.
 */
@RestController
@RequestMapping("/api")
public class MarketplacesResource {

    private final Logger log = LoggerFactory.getLogger(MarketplacesResource.class);

    private static final String ENTITY_NAME = "marketplaces";

    private final MarketplacesService marketplacesService;

    public MarketplacesResource(MarketplacesService marketplacesService) {
        this.marketplacesService = marketplacesService;
    }

    /**
     * POST  /marketplaces : Create a new marketplaces.
     *
     * @param marketplaces the marketplaces to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketplaces, or with status 400 (Bad Request) if the marketplaces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marketplaces")
    public ResponseEntity<Marketplaces> createMarketplaces(@RequestBody Marketplaces marketplaces) throws URISyntaxException {
        log.debug("REST request to save Marketplaces : {}", marketplaces);
        if (marketplaces.getId() != null) {
            throw new BadRequestAlertException("A new marketplaces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marketplaces result = marketplacesService.save(marketplaces);
        return ResponseEntity.created(new URI("/api/marketplaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marketplaces : Updates an existing marketplaces.
     *
     * @param marketplaces the marketplaces to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketplaces,
     * or with status 400 (Bad Request) if the marketplaces is not valid,
     * or with status 500 (Internal Server Error) if the marketplaces couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marketplaces")
    public ResponseEntity<Marketplaces> updateMarketplaces(@RequestBody Marketplaces marketplaces) throws URISyntaxException {
        log.debug("REST request to update Marketplaces : {}", marketplaces);
        if (marketplaces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Marketplaces result = marketplacesService.save(marketplaces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketplaces.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marketplaces : get all the marketplaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketplaces in body
     */
    @GetMapping("/marketplaces")
    public List<Marketplaces> getAllMarketplaces() {
        log.debug("REST request to get all Marketplaces");
        return marketplacesService.findAll();
    }

    /**
     * GET  /marketplaces/:id : get the "id" marketplaces.
     *
     * @param id the id of the marketplaces to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketplaces, or with status 404 (Not Found)
     */
    @GetMapping("/marketplaces/{id}")
    public ResponseEntity<Marketplaces> getMarketplaces(@PathVariable String id) {
        log.debug("REST request to get Marketplaces : {}", id);
        Optional<Marketplaces> marketplaces = marketplacesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketplaces);
    }

    /**
     * DELETE  /marketplaces/:id : delete the "id" marketplaces.
     *
     * @param id the id of the marketplaces to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marketplaces/{id}")
    public ResponseEntity<Void> deleteMarketplaces(@PathVariable String id) {
        log.debug("REST request to delete Marketplaces : {}", id);
        marketplacesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
