package com.kazi.fers;

import com.kazi.fers.business.FERProcessor;
import com.kazi.fers.business.TimeService;
import com.kazi.fers.ecb.ECBClientService;
import com.kazi.fers.sync.ECBSynchronizeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration
 */
@Configuration
public class FERConfiguration {

    @Bean
    ECBClientService productECBClientService() {
        return new ECBClientService();
    }

    @Bean
    FERProcessor productProcessor() {
        return new FERProcessor();
    }

    @Bean
    ECBSynchronizeService produceSynchronizeService() {
        return new ECBSynchronizeService();
    }

    @Bean
    TimeService produceTimeService() {
        return new TimeService();
    }
}
