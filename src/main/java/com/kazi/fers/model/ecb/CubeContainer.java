package com.kazi.fers.model.ecb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO: Add description...
 */

@XmlRootElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
public class CubeContainer {

    private List<DayCube> dayCubes;


    @XmlElement(name = "Cube")
    public List<DayCube> getDayCubes() {
        return dayCubes;
    }

    public void setDayCubes(List<DayCube> dayCubes) {
        this.dayCubes = dayCubes;
    }
}
