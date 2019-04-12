package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Mocks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Mocks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MocksRepository extends MongoRepository<Mocks, String> {

    @Query("{ 'request_url' : ?0, 'method' : ?1}")
    Optional<Mocks> findByRequestUrlAndMethod(String requestUrl, String method);

}
