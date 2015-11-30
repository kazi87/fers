package com.kazi.fers.business;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * A data/time service.
 */
@Service
public class TimeService {

    public Date now(){
        LocalDate now = getNow();
        return Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date daysBefore(int days){
        LocalDate now = getNow();
        now.minusDays(days);
        return Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    protected LocalDate getNow() {
        return LocalDate.now();
    }
}
