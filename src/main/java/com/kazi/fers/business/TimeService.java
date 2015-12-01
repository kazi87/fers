package com.kazi.fers.business;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * A data/time service.
 */
public class TimeService {

    public LocalDate now(){
        return LocalDate.now();
    }

    public boolean isWeekend(LocalDate day) {
        return DayOfWeek.SUNDAY.equals(day.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(day.getDayOfWeek());
    }
}
