package com.kazi.fers.business;

import com.kazi.fers.cache.ExRateCache;
import com.kazi.fers.model.fer.ExRate;
import com.kazi.fers.sync.ECBSynchronizeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO: Add description...
 */
@RunWith(MockitoJUnitRunner.class)
public class FERProcessorTest {

    @Mock
    private TimeService timeService;

    @Mock
    private ExRateCache cache;

    @Mock
    private ECBSynchronizeService synchronizeService;

    @InjectMocks
    private FERProcessor testObj = new FERProcessor();


    @Before
    public void setUp() {
        LocalDate testDate = LocalDate.parse("2015-11-30");
        doReturn(testDate).when(timeService).now();
        doNothing().when(synchronizeService).synchronize();
    }

    @Test
    public void shouldReadOnlyOnceFromCache() throws Exception {
        //  given
        LocalDate testDate = LocalDate.parse("2015-11-30");
        ExRate exRate = new ExRate("CHF", BigDecimal.valueOf(1.1), testDate);
        when(cache.getExRate(anyString(), any(LocalDate.class))).thenReturn(exRate);

        //  when
        testObj.process("CHF", testDate);

        //  then
        verify(cache, times(1)).getExRate(anyString(), any(LocalDate.class));
    }

    @Test
    public void shouldReadTwiceFromCache() throws Exception {
        //  given
        LocalDate testDate = LocalDate.parse("2015-11-30");
        ExRate exRate = new ExRate("CHF", BigDecimal.valueOf(1.1), testDate);
        when(cache.getExRate(anyString(), any(LocalDate.class))).thenReturn(null).thenReturn(exRate);

        //  when
        testObj.process("CHF", testDate);

        //  then
        verify(cache, times(2)).getExRate(anyString(), any(LocalDate.class));
    }

    /**
     * TODO: more tests....
     */
}