package com.kazi.fers.model.fer;

import com.kazi.fers.model.DateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * The Foreign exchange rate service response
 */
@XmlRootElement(name = "ExchangeRate")
public class ExRate {
    private String currency;
    private BigDecimal rate;
    private LocalDate date;

    private String error;

    public ExRate() {
    }

    public ExRate(String error) {
        this.error = error;
    }

    public ExRate(String currency, BigDecimal rate, LocalDate date) {
        this.currency = currency;
        this.rate = rate;
        this.date = date;
    }

    @XmlElement(name = "rate")
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @XmlElement(name = "currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name = "date")
    @XmlJavaTypeAdapter( DateAdapter.class )
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlElement(name = "error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ExRate{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExRate exRate = (ExRate) o;

        if (currency != null ? !currency.equals(exRate.currency) : exRate.currency != null) return false;
        if (rate != null ? !rate.equals(exRate.rate) : exRate.rate != null) return false;
        return !(date != null ? !date.equals(exRate.date) : exRate.date != null);

    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
