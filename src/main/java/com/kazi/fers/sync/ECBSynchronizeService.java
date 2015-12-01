package com.kazi.fers.sync;

import com.kazi.fers.cache.ExRateCache;
import com.kazi.fers.ecb.ECBClientService;
import com.kazi.fers.model.ecb.CubeContainer;
import com.kazi.fers.model.ecb.CubeEntry;
import com.kazi.fers.model.ecb.DayCube;
import com.kazi.fers.model.fer.ExRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;

/**
 * Responsible for circular updates from ECB servers...
 */
@EnableScheduling
public class ECBSynchronizeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ECBSynchronizeService.class);

    @Autowired
    private ECBClientService ecbClientService;

    @Autowired
    private ExRateCache cache;

    @PostConstruct
    private void initialSynchronization() {
        LOGGER.info("Initial FERCache synchronization...");
        synchronize();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void synchronize() {
        LOGGER.info("Synchronize service start at: " + new Date());
        CubeContainer container = ecbClientService.getLast90Days();
        if (container != null) {
            parseCubeContainer(container);
            LOGGER.warn("Synchronized local cache with ECB server.");
        } else {
            LOGGER.warn("Could not synchronize data with ECB server!");
        }
    }

    private void parseCubeContainer(CubeContainer container) {
        cache.flush();
        int i = 0;
        for (DayCube dc : container.getDayCubes()) {
            LocalDate date = dc.getTime();
            for (CubeEntry ce : dc.getEntries()) {
                ExRate exRate = new ExRate(ce.getCurrency(), ce.getRate(), date);
                cache.updateExRate(exRate);
                i++;
            }
        }
        LOGGER.debug("Updated cache with " + i + " entries.");
    }
}
