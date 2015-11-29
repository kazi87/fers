package com.kazi.fers.model.ecb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: Add description...
 */
@XmlRootElement(name = "Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
public class Envelope {

    private CubeContainer cubeContainer;

    @XmlElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
    public CubeContainer getCubeContainer() {
        return cubeContainer;
    }

    public void setCubeContainer(CubeContainer cubeContainer) {
        this.cubeContainer = cubeContainer;
    }
}
