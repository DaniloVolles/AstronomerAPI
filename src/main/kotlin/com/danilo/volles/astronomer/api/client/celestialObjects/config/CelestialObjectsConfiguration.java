package com.danilo.volles.astronomer.api.client.celestialObjects.config;

import com.danilo.volles.astronomer.api.client.celestialObjects.CelestialObjectsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CelestialObjectsConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.danilo.volles.celestial.objects.api.wsdl");
        return marshaller;
    }

    @Bean
    public CelestialObjectsClient celestialObjectsClient(Jaxb2Marshaller marshaller) {
        var client = new CelestialObjectsClient();
        client.setDefaultUri("localhost:9090/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
