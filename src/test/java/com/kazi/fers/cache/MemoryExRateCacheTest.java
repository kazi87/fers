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
        testObj.updateExRate(currency, testExRateDate, exRate);
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
        testObj.updateExRate(currency, testExRateDate, firstEntry);
        testObj.updateExRate(currency, testExRateDate, secondEntry);

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
        testObj.updateExRate(currency, firstDate, firstEntry);
        testObj.updateExRate(currency, secondDate, secondEntry);

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
        Date firstDate = formatter.parse("2015-11-30");
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate firstEntry = new ExRate(currency, firstRate, firstDate);

        //  when
        //  then
        testObj.updateExRate(currency, null, firstEntry);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfCurrencyIsNull() throws Exception {
        //  given
        String currency = "CHF";
        Date firstDate = formatter.parse("2015-11-30");
        BigDecimal firstRate = BigDecimal.valueOf(1.1);
        ExRate firstEntry = new ExRate(currency, firstRate, firstDate);

        //  when
        //  then
        testObj.updateExRate(null, firstDate, firstEntry);
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
        testObj.updateExRate(currency, firstDate, null);
    }

    @Test
    public void shouldFlushCache() throws Exception {
        //  given
        Date testExRateDate = formatter.parse("2015-11-30");
        String currency = "CHF";
        BigDecimal rate = BigDecimal.valueOf(1.1);
        ExRate exRate = new ExRate(currency, rate, testExRateDate);
        testObj.updateExRate(currency, testExRateDate, exRate);
        //  when
        testObj.flush();
        //  then
        ExRate result = testObj.getExRate(currency, testExRateDate);
        assertNull(result);
    }
}