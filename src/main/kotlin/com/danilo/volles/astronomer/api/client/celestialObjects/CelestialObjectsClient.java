package com.danilo.volles.astronomer.api.client.celestialObjects;

import com.danilo.volles.celestial.objects.api.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CelestialObjectsClient extends WebServiceGatewaySupport {

    public GetAllCelestialObjectsResponse getAllCelestialObjects() {
        GetAllCelestialObjectsRequest req = new GetAllCelestialObjectsRequest();
        return (GetAllCelestialObjectsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        "http://localhost:9090/ws",
                        req,
                        new SoapActionCallback(
                                "http://www.danilovolles.com/celestialObjects"
                        ));
    }
}
