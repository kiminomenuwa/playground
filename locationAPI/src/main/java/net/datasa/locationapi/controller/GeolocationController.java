package net.datasa.locationapi.controller;

import net.datasa.locationapi.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class GeolocationController {

    @Autowired
    private GeolocationService geolocationService;

    @GetMapping("/location")
    public Map<String, Object> getLocation() {
        return geolocationService.getLocation();
    }
}
