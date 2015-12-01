package com.kazi.fers;

import com.kazi.fers.sync.ECBSynchronizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Spring boot FER application class
 */
@SpringBootApplication
public class FERApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(FERApplication.class);

    public static void main(String[] args) {
        LOGGER.info("FERApplication is starting...");
        SpringApplication.run(FERApplication.class, args);
        LOGGER.info("FERApplication started.");

    }
}
