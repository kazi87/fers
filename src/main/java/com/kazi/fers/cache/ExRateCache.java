package com.kazi.fers.cache;

import com.kazi.fers.model.fer.ExRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * The cache interface for exchange rates
 */
@Service
public interface ExRateCache {

    ExRate getExRate(String currency, LocalDate day);

    void updateExRate(ExRate exRate);

    void flush();

}
