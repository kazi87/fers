package com.kazi.fers.business;

import java.util.Date;

import com.kazi.fers.cache.ExRateCache;
import com.kazi.fers.model.fer.ExRate;
import com.kazi.fers.sync.ECBSynchronizeService;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A business logic for FER service.
 */
@Service
public class FERProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(FERProcessor.class);

    /**
     * How many days before we can request
     */
    private int pastRange = 90;

    @Autowired
    private ExRateCache cache;

    @Autowired
    private ECBSynchronizeService synchronizeService;

    @Autowired
    private TimeService timeService;

    public ExRate process(String currency, Date day) {
        Validate.notNull(currency, "Currency is null");
        Validate.notNull(day, "Day is null");
        validateDate(day);

        return getExRate(currency, day);
    }

    private ExRate getExRate(String currency, Date day) {
        ExRate exRate = cache.getExRate(currency, day);
        if (exRate == null) {
            LOGGER.info("ExchangeRate: '" + currency + "' for the day: " + day +
                " did not find in the cache - force synchronization");
            synchronizeService.synchronize();
            exRate = cache.getExRate(currency, day);
        }

        if (exRate == null) {
            LOGGER.warn("ExchangeRate: '" + currency + "' for the day: " + day +
                " can not be read from external server!");
        }
        return exRate;
    }

    private void validateDate(Date day) {
        Date now = timeService.now();
        Date lastHandledDay = timeService.daysBefore(pastRange);
        if(day.after(now) || day.before(lastHandledDay)){
            throw new IllegalArgumentException("Given date is out of the range[90 days ago - today]: "+  day);
        }
    }

}
