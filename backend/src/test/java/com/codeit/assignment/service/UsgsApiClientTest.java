package com.codeit.assignment.service;

import com.codeit.assignment.model.Earthquake;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UsgsApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UsgsApiClient usgsApiClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(usgsApiClient, "apiUrl", "http://test-api.com");
    }

    @Test
    void testFetchEarthquakes_Success() throws Exception {
        String jsonResponse = """
                {
                  "features": [
                    {
                      "id": "us1000",
                      "properties": {
                        "mag": 5.5,
                        "place": "California",
                        "time": 1618483200000,
                        "title": "M 5.5 - California",
                        "magType": "mw"
                      }
                    },
                    {
                      "id": "us1001",
                      "properties": {
                        "mag": 1.5,
                        "place": "Small Quake",
                        "time": 1618483200000,
                        "title": "M 1.5 - Small Quake",
                        "magType": "ml"
                      }
                    }
                  ]
                }
                """;
        JsonNode rootNode = mapper.readTree(jsonResponse);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(rootNode);

        List<Earthquake> result = usgsApiClient.fetchEarthquakes();

        assertEquals(1, result.size()); // 1.5 mag should be filtered out
        assertEquals("us1000", result.get(0).getExternalId());
        assertEquals(5.5, result.get(0).getMagnitude());
    }

    @Test
    void testFetchEarthquakes_EmptyResponse() {
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> usgsApiClient.fetchEarthquakes());
    }

    @Test
    void testFetchEarthquakes_InvalidResponse() throws Exception {
        String jsonResponse = "{\"status\": \"ok\"}";
        JsonNode rootNode = mapper.readTree(jsonResponse);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(rootNode);

        assertThrows(RuntimeException.class, () -> usgsApiClient.fetchEarthquakes());
    }
}
