package com.kazi.fers.cache;

import com.kazi.fers.model.fer.ExRate;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple memory cache implementation.
 */
@Service
public class MemoryExRateCache implements ExRateCache {

    private Map<String, Map<String, ExRate>> cache = new HashMap<>();

    @Override
    public ExRate getExRate(String currency, Date day) {
        Validate.notNull(currency, "Currency can not be null");
        Validate.notNull(day, "Day can not be null");
        String dateString = getDateAsStringKey(day);
        Map<String, ExRate> dayMap = cache.get(dateString);
        if (dayMap != null) {
            return dayMap.get(currency);
        }
        return null;
    }

    @Override
    public void updateExRate(String currency, Date day, ExRate exRate) {
        Validate.notNull(currency, "Currency can not be null");
        Validate.notNull(day, "Day can not be null");
        Validate.notNull(exRate, "ExRate can not be null");

        String dayAsString = getDateAsStringKey(day);
        Map<String, ExRate> currencyMap = cache.get(dayAsString);
        if (currencyMap == null) {
            cache.put(dayAsString, new HashMap<>());
            currencyMap = cache.get(dayAsString);
        }
        currencyMap.put(currency, exRate);
    }

    @Override
    public void flush() {
        cache.clear();
    }

    private String getDateAsStringKey(Date day) {
        return DateFormat.getInstance().format(day);
    }


}
