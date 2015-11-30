package com.kazi.fers.controller;

import com.kazi.fers.business.FERProcessor;
import com.kazi.fers.model.fer.ExRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * The main Exchange Rate Service Controller.
 * TODO: in error handlers return XML with error code and description
 */
@RequestMapping("/fers")
@RestController
public class ExchangeRateServiceController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceController.class);

    @Autowired
    private FERProcessor ferProcessor;

    @ResponseBody
    @RequestMapping("/{currency}/{day}")
    public ExRate getForDay(@PathVariable String currency,
                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        LOGGER.debug("Process request with currency: " + currency + " and date: " + day);
        ExRate exRate = ferProcessor.process(currency, day);
        if (exRate == null) {
            throw new ExRateNotFoundException("Exchange rate not found for the currency: " + currency + " and date: " + day);
        }
        LOGGER.debug("Returned ExRate: " + exRate);
        return exRate;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception exception) {
        LOGGER.error("Error while request processing.", exception);
        return "Error while request processing.";
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String invalidArgument(RuntimeException ex) {
        LOGGER.error("Invalid request parameters: ", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(ExRateNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String notFound(ExRateNotFoundException ex) {
        LOGGER.error("ExRate not found: ", ex);
        return ex.getMessage();
    }
}
