package com.kazi.fers.business;

import com.kazi.fers.cache.ExRateCache;
import com.kazi.fers.model.fer.ExRate;
import com.kazi.fers.sync.ECBSynchronizeService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A business logic for FER service.
 */
public class FERProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(FERProcessor.class);

    /**
     * How many days before we can request
     * TODO: extract to configuration
     */
    private int pastRange = 90;

    @Autowired
    private ExRateCache cache;

    @Autowired
    private ECBSynchronizeService synchronizeService;

    @Autowired
    private TimeService timeService;

    public ExRate process(String currency, LocalDate day) {
        Validate.notNull(currency, "Currency is null");
        Validate.notNull(day, "Day is null");
        LocalDate validDay = getValidDay(day);
        validateDateRange(validDay);

        return getExRate(currency, validDay);
    }

    private ExRate getExRate(String currency, LocalDate day) {
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

    /**
     * @return if day is weekend, returns last friday, else return day.
     */
    private LocalDate getValidDay(LocalDate day) {
        if (timeService.isWeekend(day)) {
            day = day.with(DayOfWeek.FRIDAY);
            LOGGER.info("Changed date to weekday: " + day);
        }
        return day;
    }

    private void validateDateRange(LocalDate day) {
        LocalDate now = timeService.now();
        LocalDate lastHandledDay = now.minusDays(pastRange);
        if (day.isAfter(now) || day.isBefore(lastHandledDay)) {
            throw new IllegalArgumentException("Given date: " + day.format(DateTimeFormatter.ISO_DATE) + " is out of the range: [90 days ago] - today");
        }
    }

}
