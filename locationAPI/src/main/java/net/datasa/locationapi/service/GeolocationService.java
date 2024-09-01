package net.datasa.locationapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeolocationService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GEOLOCATION_API_URL = "https://www.googleapis.com/geolocation/v1/geolocate";

    public Map<String, Object> getLocation() {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(GEOLOCATION_API_URL)
                .queryParam("key", apiKey)
                .toUriString();

        Map<String, Object> requestBody = new HashMap<>();
        // 요청 본문에 필요한 매개변수를 추가할 수 있습니다.

        Map<String, Object> response = restTemplate.postForObject(url, requestBody, Map.class);
        return response;
    }
}
