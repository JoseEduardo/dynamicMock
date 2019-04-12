package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.DynamicMockApp;

import io.github.jhipster.application.domain.Marketplaces;
import io.github.jhipster.application.repository.MarketplacesRepository;
import io.github.jhipster.application.service.MarketplacesService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketplacesResource REST controller.
 *
 * @see MarketplacesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamicMockApp.class)
public class MarketplacesResourceIntTest {

    private static final String DEFAULT_MARKETPLACE = "AAAAAAAAAA";
    private static final String UPDATED_MARKETPLACE = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETPLACE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MARKETPLACE_URL = "BBBBBBBBBB";

    @Autowired
    private MarketplacesRepository marketplacesRepository;

    @Autowired
    private MarketplacesService marketplacesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restMarketplacesMockMvc;

    private Marketplaces marketplaces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarketplacesResource marketplacesResource = new MarketplacesResource(marketplacesService);
        this.restMarketplacesMockMvc = MockMvcBuilders.standaloneSetup(marketplacesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marketplaces createEntity() {
        Marketplaces marketplaces = new Marketplaces()
            .marketplace(DEFAULT_MARKETPLACE)
            .marketplace_url(DEFAULT_MARKETPLACE_URL);
        return marketplaces;
    }

    @Before
    public void initTest() {
        marketplacesRepository.deleteAll();
        marketplaces = createEntity();
    }

    @Test
    public void createMarketplaces() throws Exception {
        int databaseSizeBeforeCreate = marketplacesRepository.findAll().size();

        // Create the Marketplaces
        restMarketplacesMockMvc.perform(post("/api/marketplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketplaces)))
            .andExpect(status().isCreated());

        // Validate the Marketplaces in the database
        List<Marketplaces> marketplacesList = marketplacesRepository.findAll();
        assertThat(marketplacesList).hasSize(databaseSizeBeforeCreate + 1);
        Marketplaces testMarketplaces = marketplacesList.get(marketplacesList.size() - 1);
        assertThat(testMarketplaces.getMarketplace()).isEqualTo(DEFAULT_MARKETPLACE);
        assertThat(testMarketplaces.getMarketplace_url()).isEqualTo(DEFAULT_MARKETPLACE_URL);
    }

    @Test
    public void createMarketplacesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketplacesRepository.findAll().size();

        // Create the Marketplaces with an existing ID
        marketplaces.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketplacesMockMvc.perform(post("/api/marketplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketplaces)))
            .andExpect(status().isBadRequest());

        // Validate the Marketplaces in the database
        List<Marketplaces> marketplacesList = marketplacesRepository.findAll();
        assertThat(marketplacesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMarketplaces() throws Exception {
        // Initialize the database
        marketplacesRepository.save(marketplaces);

        // Get all the marketplacesList
        restMarketplacesMockMvc.perform(get("/api/marketplaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketplaces.getId())))
            .andExpect(jsonPath("$.[*].marketplace").value(hasItem(DEFAULT_MARKETPLACE.toString())))
            .andExpect(jsonPath("$.[*].marketplace_url").value(hasItem(DEFAULT_MARKETPLACE_URL.toString())));
    }
    
    @Test
    public void getMarketplaces() throws Exception {
        // Initialize the database
        marketplacesRepository.save(marketplaces);

        // Get the marketplaces
        restMarketplacesMockMvc.perform(get("/api/marketplaces/{id}", marketplaces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketplaces.getId()))
            .andExpect(jsonPath("$.marketplace").value(DEFAULT_MARKETPLACE.toString()))
            .andExpect(jsonPath("$.marketplace_url").value(DEFAULT_MARKETPLACE_URL.toString()));
    }

    @Test
    public void getNonExistingMarketplaces() throws Exception {
        // Get the marketplaces
        restMarketplacesMockMvc.perform(get("/api/marketplaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMarketplaces() throws Exception {
        // Initialize the database
        marketplacesService.save(marketplaces);

        int databaseSizeBeforeUpdate = marketplacesRepository.findAll().size();

        // Update the marketplaces
        Marketplaces updatedMarketplaces = marketplacesRepository.findById(marketplaces.getId()).get();
        updatedMarketplaces
            .marketplace(UPDATED_MARKETPLACE)
            .marketplace_url(UPDATED_MARKETPLACE_URL);

        restMarketplacesMockMvc.perform(put("/api/marketplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketplaces)))
            .andExpect(status().isOk());

        // Validate the Marketplaces in the database
        List<Marketplaces> marketplacesList = marketplacesRepository.findAll();
        assertThat(marketplacesList).hasSize(databaseSizeBeforeUpdate);
        Marketplaces testMarketplaces = marketplacesList.get(marketplacesList.size() - 1);
        assertThat(testMarketplaces.getMarketplace()).isEqualTo(UPDATED_MARKETPLACE);
        assertThat(testMarketplaces.getMarketplace_url()).isEqualTo(UPDATED_MARKETPLACE_URL);
    }

    @Test
    public void updateNonExistingMarketplaces() throws Exception {
        int databaseSizeBeforeUpdate = marketplacesRepository.findAll().size();

        // Create the Marketplaces

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketplacesMockMvc.perform(put("/api/marketplaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketplaces)))
            .andExpect(status().isBadRequest());

        // Validate the Marketplaces in the database
        List<Marketplaces> marketplacesList = marketplacesRepository.findAll();
        assertThat(marketplacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMarketplaces() throws Exception {
        // Initialize the database
        marketplacesService.save(marketplaces);

        int databaseSizeBeforeDelete = marketplacesRepository.findAll().size();

        // Delete the marketplaces
        restMarketplacesMockMvc.perform(delete("/api/marketplaces/{id}", marketplaces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Marketplaces> marketplacesList = marketplacesRepository.findAll();
        assertThat(marketplacesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marketplaces.class);
        Marketplaces marketplaces1 = new Marketplaces();
        marketplaces1.setId("id1");
        Marketplaces marketplaces2 = new Marketplaces();
        marketplaces2.setId(marketplaces1.getId());
        assertThat(marketplaces1).isEqualTo(marketplaces2);
        marketplaces2.setId("id2");
        assertThat(marketplaces1).isNotEqualTo(marketplaces2);
        marketplaces1.setId(null);
        assertThat(marketplaces1).isNotEqualTo(marketplaces2);
    }
}
