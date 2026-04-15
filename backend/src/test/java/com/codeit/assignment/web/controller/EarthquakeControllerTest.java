package com.codeit.assignment.web.controller;

import com.codeit.assignment.model.Earthquake;
import com.codeit.assignment.service.EarthquakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EarthquakeControllerTest {

    @Mock
    private EarthquakeService earthquakeService;

    @InjectMocks
    private EarthquakeController earthquakeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndStore() {
        List<Earthquake> earthquakes = Arrays.asList(new Earthquake(), new Earthquake());
        when(earthquakeService.fetchAndStore()).thenReturn(earthquakes);

        ResponseEntity<List<Earthquake>> response = earthquakeController.fetchAndStore();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(earthquakeService, times(1)).fetchAndStore();
    }

    @Test
    void testGetAll() {
        List<Earthquake> earthquakes = Arrays.asList(new Earthquake());
        when(earthquakeService.getAll()).thenReturn(earthquakes);

        ResponseEntity<List<Earthquake>> response = earthquakeController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(earthquakeService, times(1)).getAll();
    }

    @Test
    void testFilterByMagnitude() {
        double minMag = 4.5;
        List<Earthquake> earthquakes = Arrays.asList(new Earthquake());
        when(earthquakeService.getByMinMagnitude(minMag)).thenReturn(earthquakes);

        ResponseEntity<List<Earthquake>> response = earthquakeController.filterByMagnitude(minMag);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(earthquakeService, times(1)).getByMinMagnitude(minMag);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        doNothing().when(earthquakeService).deleteById(id);

        ResponseEntity<Void> response = earthquakeController.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(earthquakeService, times(1)).deleteById(id);
    }
}
