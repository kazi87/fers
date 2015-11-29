package com.kazi.fers.model.ecb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * TODO: Add description...
 */
@XmlRootElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
public class DayCube {

    private Date time;

    private List<CubeEntry> entries;

    @XmlAttribute(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlElement(name = "Cube")
    public List<CubeEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<CubeEntry> entries) {
        this.entries = entries;
    }
}
