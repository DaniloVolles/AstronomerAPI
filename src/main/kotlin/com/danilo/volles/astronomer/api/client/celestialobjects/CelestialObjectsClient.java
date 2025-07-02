package com.danilo.volles.astronomer.api.client.celestialobjects;

import com.danilo.volles.astronomer.api.exception.ObjectNotFoundException;
import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameRequest;
import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * SOAP client responsible for consuming the Celestial Objects API.
 *
 * <p>
 * This client communicates with a remote SOAP WebService located at {@code http://host:9090/ws},
 * using {@link WebServiceGatewaySupport} and a fixed SOAP Action callback.
 */
@Component
public class CelestialObjectsClient extends WebServiceGatewaySupport {

    private final String DESTINATION_URI = "http://localhost:9090/ws";
    private final String ACTION_CALLBACK = "http://www.danilovolles.com/celestialObjects";

    /**
     * Sends a request with the object name and expects a typed response, or throws an
     * {@link ObjectNotFoundException} if no object is found.
     * @param celestialObjectName
     * @return
     */
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
