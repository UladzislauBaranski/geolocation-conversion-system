package com.gmail.vladbaransky.geolocationconversionsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.geolocationconversionsystem.service.model.Geolocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YandexGeolocationServiceImpl implements GeolocationService {
    @Value("${url}")
    private String URL;

    @Value("${result.count}")
    private String RESULT_COUNT;

    @Override
    @Cacheable("geolocation")
    public Geolocation getGeolocation(String parameter) throws JsonProcessingException {
        Geolocation geolocation = new Geolocation();
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = URL +parameter+RESULT_COUNT;

        ResponseEntity<String> forEntity = restTemplate.getForEntity(fooResourceUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(forEntity.getBody());
        JsonNode name = root.path("response");
        String coordinates = name.findValue("pos").toString();
        String address = name.findValue("text").toString();

        geolocation.setCoordinates(coordinates);
        geolocation.setAddress(address);
        return geolocation;
    }
}
