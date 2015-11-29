package com.kazi.fers;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Spring boot FER application class
 */
@SpringBootApplication
public class FERApplication {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FERApplication.class);

    public static void main(String[] args) {
        LOGGER.info("FERApplication is starting...");
        ApplicationContext ctx = SpringApplication.run(FERApplication.class, args);
        LOGGER.info("FERApplication started.");

    }
}
