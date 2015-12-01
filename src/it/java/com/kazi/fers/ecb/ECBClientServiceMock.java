package com.kazi.fers.ecb;

import com.kazi.fers.ecb.ECBClientService;
import com.kazi.fers.model.ecb.CubeContainer;
import com.kazi.fers.model.ecb.Envelope;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Mock ECB Service
 */
public class ECBClientServiceMock extends ECBClientService {

    private final Envelope envelope;

    public ECBClientServiceMock() {
        try {
            JAXBContext jc = JAXBContext.newInstance(Envelope.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("sampleECBResponse");
            envelope = (Envelope) unmarshaller.unmarshal(is);
        }catch (Exception e){
            throw new IllegalStateException("Can not initialize ECBClientServiceMock", e);
        }

    }

    @Override
    public CubeContainer getLast90Days() {
        return envelope.getCubeContainer();
    }
}
