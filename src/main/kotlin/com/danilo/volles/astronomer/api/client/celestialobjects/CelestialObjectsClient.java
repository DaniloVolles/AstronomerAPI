package com.danilo.volles.astronomer.api.client.celestialobjects;

import com.danilo.volles.astronomer.api.exception.ObjectNotFoundException;
import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameRequest;
import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CelestialObjectsClient extends WebServiceGatewaySupport {

    private final String DESTINATION_URI = "http://localhost:9090/ws";
    private final String ACTION_CALLBACK = "http://www.danilovolles.com/celestialObjects";

    public GetCelestialObjectByNameResponse getCelestialObjectByName(String celestialObjectName) {

        GetCelestialObjectByNameRequest request = new GetCelestialObjectByNameRequest();
        request.setName(celestialObjectName);

        try {
            return (GetCelestialObjectByNameResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(
                            DESTINATION_URI,
                            request,
                            new SoapActionCallback(ACTION_CALLBACK)
                    );

        } catch (SoapFaultClientException e) {
            String message = e.getMessage();
            if (message != null && message.contains("celestialObjects") && message.contains("null")) {
                throw new ObjectNotFoundException("No celestial object found with name " + celestialObjectName);
            }
            throw e;
        }
    }
}
