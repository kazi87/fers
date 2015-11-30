package com.kazi.fers.controller;

/**
 * Not found exception
 */
public class ExRateNotFoundException extends RuntimeException {
    public ExRateNotFoundException(String message) {
        super(message);
    }
}
