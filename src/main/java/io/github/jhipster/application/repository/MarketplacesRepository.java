package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Marketplaces;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Marketplaces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketplacesRepository extends MongoRepository<Marketplaces, String> {

    @Query("{ 'marketplace' : ?0, 'marketplace_url' : ?1}")
    Optional<Marketplaces> findByMarketplaceAndMarketplaceUrl(String marketplace, String marketplaceUrl);

    @Query("{ 'marketplace' : ?0}")
    Optional<Marketplaces> findByMarketplace(String marketplace);

}
