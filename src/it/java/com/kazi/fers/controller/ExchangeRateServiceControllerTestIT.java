package com.kazi.fers.controller;

import com.kazi.fers.FERApplication;
import com.kazi.fers.model.fer.ExRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Simple integration tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {FERApplication.class, IntegrationTestConfiguration.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ExchangeRateServiceControllerTestIT {

    @Value("${local.server.port}")
    private String port;

    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new TestRestTemplate();
    }

    @Test
    public void getExRateShouldNotBeEmpty() throws Exception {
        //  given
        StringBuilder baseURL = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-21");

        //  when
        ResponseEntity<ExRate> entity = template.getForEntity(baseURL.toString(), ExRate.class);
        // then
        assertNotNull(entity.getBody());
    }

    @Test
    public void shouldReturnValidExRateAndCurrencyFor2015_11_17() throws Exception {
        //  given
        StringBuilder baseURL = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-17");
        BigDecimal expectedRate = BigDecimal.valueOf(1.0806);

        //  when
        ExRate entity = template.getForEntity(baseURL.toString(), ExRate.class).getBody();

        // then
        assertEquals("CHF", entity.getCurrency());
        assertEquals(expectedRate, entity.getRate());
    }

    @Test
    public void shouldReturnStatus200() throws Exception {
        //  given
        StringBuilder baseURL = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-15");

        //  when
        HttpStatus status = template.getForEntity(baseURL.toString(), ExRate.class).getStatusCode();

        // then
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    public void shouldReturnFridaysExRateForWeekend() throws Exception {
        //  given
        StringBuilder baseURLSunday = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-22");
        StringBuilder baseURLSaturday = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-21");
        BigDecimal expectedRate = BigDecimal.valueOf(1.0844);
        LocalDate expectedDate = LocalDate.parse("2015-11-20");

        //  when
        ExRate rateSaturday = template.getForEntity(baseURLSaturday.toString(), ExRate.class).getBody();
        ExRate rateSunday = template.getForEntity(baseURLSunday.toString(), ExRate.class).getBody();

        // then
        assertEquals(expectedDate, rateSaturday.getDate());
        assertEquals(expectedRate, rateSaturday.getRate());

        assertEquals(expectedRate, rateSunday.getRate());
        assertEquals(expectedDate, rateSunday.getDate());
    }

    @Test
    public void errorShouldBeNullIfExRateExists() throws Exception {
        //  given
        StringBuilder baseURL = new StringBuilder("http://localhost:").append(port).append("/fers/CHF/2015-11-17");

        //  when
        ExRate rate = template.getForEntity(baseURL.toString(), ExRate.class).getBody();

        // then
        assertNull(rate.getError());
    }


    @Test
    public void shouldReturnErrorIfNotExistingCurrency() throws Exception {
        //  given
        StringBuilder baseURL = new StringBuilder("http://localhost:").append(port).append("/fers/XXX/2015-11-17");

        //  when
        ExRate rate = template.getForEntity(baseURL.toString(), ExRate.class).getBody();

        // then
        assertNotNull(rate.getError());
    }


}
