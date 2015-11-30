package com.kazi.fers.cache;

import com.kazi.fers.model.fer.ExRate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The cache interface for exchange rates
 */
@Service
public interface ExRateCache {

    ExRate getExRate(String currency, Date day);

    void updateExRate(String currency, Date day, ExRate exRate);

    void flush();

}
