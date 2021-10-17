package com.gmail.vladbaransky.geolocationconversionsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.vladbaransky.geolocationconversionsystem.service.GeolocationService;
import com.gmail.vladbaransky.geolocationconversionsystem.service.model.Geolocation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class GeolocationController {
    private final GeolocationService geolocationService;

    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping
    public ResponseEntity<?> getGeolocation(@RequestParam String parameter) throws JsonProcessingException {
        return ResponseEntity.ok(geolocationService.getGeolocation(parameter));
    }
}
