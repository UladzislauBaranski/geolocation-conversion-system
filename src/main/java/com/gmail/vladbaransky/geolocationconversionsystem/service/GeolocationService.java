package com.gmail.vladbaransky.geolocationconversionsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.vladbaransky.geolocationconversionsystem.service.model.Geolocation;
import org.springframework.http.ResponseEntity;

public interface GeolocationService {
    Geolocation getGeolocation(String parameter) throws JsonProcessingException;
}
