package com.kazi.fers.ecb;

import com.kazi.fers.model.ecb.DayCube;
import com.kazi.fers.model.ecb.Envelope;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for ECB Service, based on sample response: sampleECBResponse.xml
 */
public class ECBClientServiceTest {

    public static final String ECB_90_DAYS_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    private RestTemplate restTemplate = new RestTemplate();
    private Consumer<DayCube> dayCubeNotEmptyConsumer;
    private Consumer<DayCube> dayCubeAndDayEntryNotEmptyConsumer;

    @Before
    public void setUp() throws Exception {
        initRestMocks();

        //  Setup test customers
        dayCubeNotEmptyConsumer = dayCube -> {
            assertNotNull(dayCube.getTime());
            assertNotNull(dayCube.getEntries());
        };

        dayCubeAndDayEntryNotEmptyConsumer = dayCube -> {
            assertNotNull(dayCube.getTime());
            assertNotNull(dayCube.getEntries());
            dayCube.getEntries().stream().forEach(cubeEntry -> {
                assertNotNull(cubeEntry.getCurrency());
                assertNotNull(cubeEntry.getRate());
            });
        };
    }

    private void initRestMocks() throws IOException {
        //  Setup RestTemplate mock
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        InputStream is = this.getClass().getResourceAsStream("sampleECBResponse");
        byte[] bytes = IOUtils.toByteArray(is);

        mockServer.expect(requestTo(ECB_90_DAYS_URL)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(bytes, MediaType.APPLICATION_XML));
    }

    @Test
    public void envelopeFor90DaysRequestShouldNotBeNull() throws Exception {
        //  given
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
        //  when
        Envelope testObj = restTemplate.getForObject(url, Envelope.class);
        //  then
        assertNotNull(testObj);
    }

    @Test
    public void envelopeFor90ShouldContainCubeContainer() throws Exception {
        //  given
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
        //  when
        Envelope testObj = restTemplate.getForObject(url, Envelope.class);
        //  then
        assertNotNull(testObj.getCubeContainer());
    }

    @Test
    public void cubeContainerShouldContain65DayCubeElements() throws Exception {
        //  given
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
        //  when
        Envelope envelope = restTemplate.getForObject(url, Envelope.class);
        List<DayCube> testObj = envelope.getCubeContainer().getDayCubes();

        //  then
        assertNotNull(testObj);
        assertEquals(65, testObj.size());
    }

    @Test
    public void eachDayCubeShouldContainTimeAndEntry() throws Exception {
        //  given
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
        //  when
        Envelope envelope = restTemplate.getForObject(url, Envelope.class);
        List<DayCube> testObj = envelope.getCubeContainer().getDayCubes();

        //  then
        testObj.stream().forEach(dayCubeNotEmptyConsumer);
    }

    @Test
    public void eachCubeEntryShouldContainCurrencyAndExRate() throws Exception {
        //  given
        String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
        //  when
        Envelope envelope = restTemplate.getForObject(url, Envelope.class);
        List<DayCube> testObj = envelope.getCubeContainer().getDayCubes();

        //  then
        testObj.stream().forEach(dayCubeAndDayEntryNotEmptyConsumer);
    }

}