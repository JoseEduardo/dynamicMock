package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.DynamicMockApp;

import io.github.jhipster.application.domain.Mocks;
import io.github.jhipster.application.domain.MocksHeader;
import io.github.jhipster.application.repository.MocksRepository;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MocksResource REST controller.
 *
 * @see MocksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamicMockApp.class)
public class MocksResourceIntTest {

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_URL = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_URL = "BBBBBBBBBB";

    private static final List<MocksHeader> DEFAULT_REQUEST_HEADERS = new ArrayList<>();
    private static final List<MocksHeader> UPDATED_REQUEST_HEADERS = new ArrayList<>();

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

    private static final List<MocksHeader> DEFAULT_RESPONSE_HEADERS = new ArrayList<>();
    private static final List<MocksHeader> UPDATED_RESPONSE_HEADERS = new ArrayList<>();

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETPLACE = "AAAAAAAAAA";
    private static final String UPDATED_MARKETPLACE = "BBBBBBBBBB";

    @Autowired
    private MocksRepository mocksRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restMocksMockMvc;

    private Mocks mocks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MocksResource mocksResource = new MocksResource(mocksRepository);
        this.restMocksMockMvc = MockMvcBuilders.standaloneSetup(mocksResource)
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
    public static Mocks createEntity() {
        Mocks mocks = new Mocks()
            .method(DEFAULT_METHOD)
            .request_url(DEFAULT_REQUEST_URL)
            .request_headers(DEFAULT_REQUEST_HEADERS)
            .request_body(DEFAULT_REQUEST_BODY)
            .response_headers(DEFAULT_RESPONSE_HEADERS)
            .response_body(DEFAULT_RESPONSE_BODY)
            .response_status(DEFAULT_RESPONSE_STATUS)
            .marketplace(DEFAULT_MARKETPLACE);
        return mocks;
    }

    @Before
    public void initTest() {
        mocksRepository.deleteAll();
        mocks = createEntity();
    }

    @Test
    public void createMocks() throws Exception {
        int databaseSizeBeforeCreate = mocksRepository.findAll().size();

        // Create the Mocks
        restMocksMockMvc.perform(post("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mocks)))
            .andExpect(status().isCreated());

        // Validate the Mocks in the database
        List<Mocks> mocksList = mocksRepository.findAll();
        assertThat(mocksList).hasSize(databaseSizeBeforeCreate + 1);
        Mocks testMocks = mocksList.get(mocksList.size() - 1);
        assertThat(testMocks.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testMocks.getRequest_url()).isEqualTo(DEFAULT_REQUEST_URL);
        assertThat(testMocks.getRequest_headers()).isEqualTo(DEFAULT_REQUEST_HEADERS);
        assertThat(testMocks.getRequest_body()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testMocks.getResponse_headers()).isEqualTo(DEFAULT_RESPONSE_HEADERS);
        assertThat(testMocks.getResponse_body()).isEqualTo(DEFAULT_RESPONSE_BODY);
        assertThat(testMocks.getResponse_status()).isEqualTo(DEFAULT_RESPONSE_STATUS);
        assertThat(testMocks.getMarketplace()).isEqualTo(DEFAULT_MARKETPLACE);
    }

    @Test
    public void createMocksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mocksRepository.findAll().size();

        // Create the Mocks with an existing ID
        mocks.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMocksMockMvc.perform(post("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mocks)))
            .andExpect(status().isBadRequest());

        // Validate the Mocks in the database
        List<Mocks> mocksList = mocksRepository.findAll();
        assertThat(mocksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMocks() throws Exception {
        // Initialize the database
        mocksRepository.save(mocks);

        // Get all the mocksList
        restMocksMockMvc.perform(get("/api/mocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mocks.getId())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].request_url").value(hasItem(DEFAULT_REQUEST_URL.toString())))
            .andExpect(jsonPath("$.[*].request_headers").value(hasItem(DEFAULT_REQUEST_HEADERS.toString())))
            .andExpect(jsonPath("$.[*].request_body").value(hasItem(DEFAULT_REQUEST_BODY.toString())))
            .andExpect(jsonPath("$.[*].response_headers").value(hasItem(DEFAULT_RESPONSE_HEADERS.toString())))
            .andExpect(jsonPath("$.[*].response_body").value(hasItem(DEFAULT_RESPONSE_BODY.toString())))
            .andExpect(jsonPath("$.[*].response_status").value(hasItem(DEFAULT_RESPONSE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].marketplace").value(hasItem(DEFAULT_MARKETPLACE.toString())));
    }
    
    @Test
    public void getMocks() throws Exception {
        // Initialize the database
        mocksRepository.save(mocks);

        // Get the mocks
        restMocksMockMvc.perform(get("/api/mocks/{id}", mocks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mocks.getId()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.request_url").value(DEFAULT_REQUEST_URL.toString()))
            .andExpect(jsonPath("$.request_headers").value(DEFAULT_REQUEST_HEADERS.toString()))
            .andExpect(jsonPath("$.request_body").value(DEFAULT_REQUEST_BODY.toString()))
            .andExpect(jsonPath("$.response_headers").value(DEFAULT_RESPONSE_HEADERS.toString()))
            .andExpect(jsonPath("$.response_body").value(DEFAULT_RESPONSE_BODY.toString()))
            .andExpect(jsonPath("$.response_status").value(DEFAULT_RESPONSE_STATUS.toString()))
            .andExpect(jsonPath("$.marketplace").value(DEFAULT_MARKETPLACE.toString()));
    }

    @Test
    public void getNonExistingMocks() throws Exception {
        // Get the mocks
        restMocksMockMvc.perform(get("/api/mocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMocks() throws Exception {
        // Initialize the database
        mocksRepository.save(mocks);

        int databaseSizeBeforeUpdate = mocksRepository.findAll().size();

        // Update the mocks
        Mocks updatedMocks = mocksRepository.findById(mocks.getId()).get();
        updatedMocks
            .method(UPDATED_METHOD)
            .request_url(UPDATED_REQUEST_URL)
            .request_headers(UPDATED_REQUEST_HEADERS)
            .request_body(UPDATED_REQUEST_BODY)
            .response_headers(UPDATED_RESPONSE_HEADERS)
            .response_body(UPDATED_RESPONSE_BODY)
            .response_status(UPDATED_RESPONSE_STATUS)
            .marketplace(UPDATED_MARKETPLACE);

        restMocksMockMvc.perform(put("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMocks)))
            .andExpect(status().isOk());

        // Validate the Mocks in the database
        List<Mocks> mocksList = mocksRepository.findAll();
        assertThat(mocksList).hasSize(databaseSizeBeforeUpdate);
        Mocks testMocks = mocksList.get(mocksList.size() - 1);
        assertThat(testMocks.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testMocks.getRequest_url()).isEqualTo(UPDATED_REQUEST_URL);
        assertThat(testMocks.getRequest_headers()).isEqualTo(UPDATED_REQUEST_HEADERS);
        assertThat(testMocks.getRequest_body()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testMocks.getResponse_headers()).isEqualTo(UPDATED_RESPONSE_HEADERS);
        assertThat(testMocks.getResponse_body()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testMocks.getResponse_status()).isEqualTo(UPDATED_RESPONSE_STATUS);
        assertThat(testMocks.getMarketplace()).isEqualTo(UPDATED_MARKETPLACE);
    }

    @Test
    public void updateNonExistingMocks() throws Exception {
        int databaseSizeBeforeUpdate = mocksRepository.findAll().size();

        // Create the Mocks

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMocksMockMvc.perform(put("/api/mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mocks)))
            .andExpect(status().isBadRequest());

        // Validate the Mocks in the database
        List<Mocks> mocksList = mocksRepository.findAll();
        assertThat(mocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMocks() throws Exception {
        // Initialize the database
        mocksRepository.save(mocks);

        int databaseSizeBeforeDelete = mocksRepository.findAll().size();

        // Delete the mocks
        restMocksMockMvc.perform(delete("/api/mocks/{id}", mocks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mocks> mocksList = mocksRepository.findAll();
        assertThat(mocksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mocks.class);
        Mocks mocks1 = new Mocks();
        mocks1.setId("id1");
        Mocks mocks2 = new Mocks();
        mocks2.setId(mocks1.getId());
        assertThat(mocks1).isEqualTo(mocks2);
        mocks2.setId("id2");
        assertThat(mocks1).isNotEqualTo(mocks2);
        mocks1.setId(null);
        assertThat(mocks1).isNotEqualTo(mocks2);
    }
}
