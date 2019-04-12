package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.repository.MocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Marketplaces.
 */
@Service
public class MocksService {

    private final Logger log = LoggerFactory.getLogger(MocksService.class);

    private final MocksRepository mocksRepository;

    public MocksService(MocksRepository mocksRepository) {
        this.mocksRepository = mocksRepository;
    }

    /**
     * Save a marketplaces.
     *
     * @param mocks the entity to save
     * @return the persisted entity
     */
    public Mocks save(Mocks mocks) {
        log.debug("Request to save Mock : {}", mocks);
        return mocksRepository.save(mocks);
    }

    /**
     * Get all the mocks.
     *
     * @return the list of entities
     */
    public List<Mocks> findAll() {
        log.debug("Request to get all Mocks");
        return mocksRepository.findAll();
    }


    /**
     * Get one mocks by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Mocks> findOne(String id) {
        log.debug("Request to get Mocks : {}", id);
        return mocksRepository.findById(id);
    }

    /**
     * Delete the mocks by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Mocks : {}", id);
        mocksRepository.deleteById(id);
    }

    public Optional<Mocks> findByRequestUrlAndMethod(String requestUrl, String method) {
       return mocksRepository.findByRequestUrlAndMethod(requestUrl, method);
    }
}
