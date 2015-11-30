package com.kazi.fers.ecb;

import com.kazi.fers.model.ecb.CubeContainer;
import com.kazi.fers.model.ecb.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * The ECB client service.
 * TODO: TEST!
 */
@Service
public class ECBClientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ECBClientService.class);

    private final static String ECB_90_DAYS_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    private final RestTemplate template = new RestTemplate();

    public CubeContainer getLast90Days() {
        try {
            Envelope envelope = template.getForObject(ECB_90_DAYS_URL, Envelope.class);
            if (envelope != null) {
                LOGGER.info("Read CubeContainer from remote service...");
                return envelope.getCubeContainer();
            }
        } catch (RestClientException e) {
            throw new IllegalStateException("Can not read data from the url: " + ECB_90_DAYS_URL, e);
        }
        return null;
    }
}
