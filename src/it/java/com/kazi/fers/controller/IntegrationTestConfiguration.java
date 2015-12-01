package com.kazi.fers.controller;

import com.kazi.fers.ecb.ECBClientService;
import com.kazi.fers.ecb.ECBClientServiceMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Test configuration overwritten some of the services by mock
 */
@Configuration
@Order(2147483646)
public class IntegrationTestConfiguration {

    @Bean
    ECBClientService productECBClientService() {
        return new ECBClientServiceMock();
    }
}
