package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Marketplaces;
import io.github.jhipster.application.repository.MarketplacesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Marketplaces.
 */
@Service
public class MarketplacesService {

    private final Logger log = LoggerFactory.getLogger(MarketplacesService.class);

    private final MarketplacesRepository marketplacesRepository;

    public MarketplacesService(MarketplacesRepository marketplacesRepository) {
        this.marketplacesRepository = marketplacesRepository;
    }

    /**
     * Save a marketplaces.
     *
     * @param marketplaces the entity to save
     * @return the persisted entity
     */
    public Marketplaces save(Marketplaces marketplaces) {
        log.debug("Request to save Marketplaces : {}", marketplaces);
        String marketplaceUrl = marketplaces.getMarketplace_url();
        char c = marketplaceUrl.charAt(marketplaceUrl.length() - 1);
        if (c == '/') {
            marketplaceUrl = marketplaceUrl.substring(0, marketplaceUrl.length()-1);
        }

        marketplaces.setMarketplace_url(marketplaceUrl);
        return marketplacesRepository.save(marketplaces);
    }

    /**
     * Get all the marketplaces.
     *
     * @return the list of entities
     */
    public List<Marketplaces> findAll() {
        log.debug("Request to get all Marketplaces");
        return marketplacesRepository.findAll();
    }


    /**
     * Get one marketplaces by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Marketplaces> findOne(String id) {
        log.debug("Request to get Marketplaces : {}", id);
        return marketplacesRepository.findById(id);
    }

    /**
     * Delete the marketplaces by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Marketplaces : {}", id);
        marketplacesRepository.deleteById(id);
    }

    public Optional<Marketplaces> findMyMarketplace(String marketplace) {
        return marketplacesRepository.findByMarketplace(marketplace);
    }
}
