package com.kazi.fers.model.ecb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * TODO: Add description...
 */
@XmlRootElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
public class DayCube {

    private LocalDate time;

    private List<CubeEntry> entries;

    @XmlAttribute(name = "time")
    @XmlJavaTypeAdapter( DateAdapter.class )
    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    @XmlElement(name = "Cube")
    public List<CubeEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<CubeEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "DayCube{" +
                "time=" + time +
                ", entries=" + entries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayCube dayCube = (DayCube) o;

        if (time != null ? !time.equals(dayCube.time) : dayCube.time != null) return false;
        return !(entries != null ? !entries.equals(dayCube.entries) : dayCube.entries != null);

    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }
}
