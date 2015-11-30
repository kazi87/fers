package com.kazi.fers.cache;

import com.kazi.fers.model.fer.ExRate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * TODO: Add description...
 */
public class MemoryExRateCacheTest {

    private ExRateCache testObj;
    private DateFormat formatter;

    @Before
    public void setUp() throws Exception {
        testObj = new MemoryExRateCache();

        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void shouldReturnNothingIfEmpty() throws Exception {
        //  given
        Date testExRateDate = formatter.parse("2015-11-30");
        //  when
        ExRate result = testObj.getExRate("CHF", testExRateDate);
        //  then
        assertNull(result);
    }

    @Test
    public void shouldAddEntry() throws Exception {
        //  given
        Date testExRateDate = formatter.parse("2015-11-30");
        String currency = "CHF";
        BigDecimal rate = BigDecimal.valueOf(1.1);
        ExRate exRate = new ExRate(currency, rate, testExRateDate);
        //  when
        testObj.updateExRate(exRate);
        //  then
        ExRate result = testObj.getExRate(currency, testExRateDate);
        assertEquals(exRate, result);
    }

    @Test
    public void shouldReturnLastEntryForTheSameQuery() throws Exception {
        //  given
        Date testExRateDate = formatter.parse("2015-11-30");
        String currency = "CHF";
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        BigDecimal secondRate = BigDecimal.valueOf(1.2);
        ExRate firstEntry = new ExRate(currency, firstRate, testExRateDate);
        ExRate secondEntry = new ExRate(currency, secondRate, testExRateDate);

        //  when
        testObj.updateExRate(firstEntry);
        testObj.updateExRate(secondEntry);

        //  then
        ExRate result = testObj.getExRate(currency, testExRateDate);
        assertEquals(secondEntry, result);
    }

    @Test
    public void shouldReturnEntryForGivenDate() throws Exception {
        //  given
        String currency = "CHF";
        Date firstDate = formatter.parse("2015-11-30");
        Date secondDate = formatter.parse("2015-11-29");
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        BigDecimal secondRate = BigDecimal.valueOf(1.2);
        ExRate firstEntry = new ExRate(currency, firstRate, firstDate);
        ExRate secondEntry = new ExRate(currency, secondRate, secondDate);
        testObj.updateExRate(firstEntry);
        testObj.updateExRate(secondEntry);

        //  when
        ExRate firstResult = testObj.getExRate(currency, firstDate);
        ExRate secondResult = testObj.getExRate(currency, secondDate);

        //  then
        assertEquals(firstEntry, firstResult);
        assertEquals(secondEntry, secondResult);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfDateIsNull() throws Exception {
        //  given
        String currency = "CHF";
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate firstEntry = new ExRate(currency, firstRate, null);

        //  when
        //  then
        testObj.updateExRate(firstEntry);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfCurrencyIsNull() throws Exception {
        //  given
        Date firstDate = formatter.parse("2015-11-30");
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate firstEntry = new ExRate(null, firstRate, firstDate);

        //  when
        //  then
        testObj.updateExRate(firstEntry);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfExRateIsNull() throws Exception {
        //  given
        String currency = "CHF";
        Date firstDate = formatter.parse("2015-11-30");
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate firstEntry = new ExRate(currency, firstRate, firstDate);

        //  when
        //  then
        testObj.updateExRate(null);
    }

    @Test
    public void shouldFlushCache() throws Exception {
        //  given
        Date testExRateDate = formatter.parse("2015-11-30");
        String currency = "CHF";
        BigDecimal rate = BigDecimal.valueOf(1.1);
        ExRate exRate = new ExRate(currency, rate, testExRateDate);
        testObj.updateExRate(exRate);
        //  when
        testObj.flush();
        //  then
        ExRate result = testObj.getExRate(currency, testExRateDate);
        assertNull(result);
    }

    @Test
    public void shouldReturnEntryForTheSameDateButDifferentTime() throws Exception {
        //  given
        Date exRateDate = formatter.parse("2015-11-30");
        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date firstExRateDate = formatter.parse("2015-11-30 11:04");
        String currency = "CHF";
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate entry = new ExRate(currency, firstRate, firstExRateDate);

        //  when
        testObj.updateExRate(entry);

        //  then
        ExRate result = testObj.getExRate(currency, exRateDate);
        assertEquals(entry, result);
    }
}