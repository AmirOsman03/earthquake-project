package com.codeit.assignment.service;

import com.codeit.assignment.model.Earthquake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsgsApiClient {

    private static final Logger log = LoggerFactory.getLogger(UsgsApiClient.class);

    @Value("${usgs.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public UsgsApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Earthquake> fetchEarthquakes() {
        try {
            JsonNode root = restTemplate.getForObject(apiUrl, JsonNode.class);

            if (root == null || !root.has("features")) {
                throw new RuntimeException("Invalid response from USGS API");
            }

            JsonNode features = root.get("features");
            log.info("Total features received from USGS: {}", features.size());

            List<Earthquake> earthquakes = new ArrayList<>();

            for (JsonNode feature : features) {
                JsonNode props = feature.get("properties");
                if (props == null) continue;

                JsonNode magNode = props.get("mag");
                if (magNode == null || magNode.isNull()) continue;

                double mag = magNode.asDouble();
                log.info("Earthquake mag: {}", mag);

                if (mag <= 2.0) continue;

                earthquakes.add(Earthquake.builder()
                        .externalId(feature.get("id").asText(null))
                        .magnitude(mag)
                        .magType(props.path("magType").asString(null))
                        .place(props.path("place").asString(null))
                        .title(props.path("title").asString(null))
                        .time(props.path("time").asLong())
                        .build());
            }

            log.info("Earthquakes after filter: {}", earthquakes.size());
            return earthquakes;

        } catch (RestClientException e) {
            throw new RuntimeException("USGS API is unavailable: " + e.getMessage());
        }
    }
}