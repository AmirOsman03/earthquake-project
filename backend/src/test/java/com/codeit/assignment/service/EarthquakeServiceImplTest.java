package com.codeit.assignment.service;

import com.codeit.assignment.model.Earthquake;
import com.codeit.assignment.repository.EarthquakeRepository;
import com.codeit.assignment.service.impl.EarthquakeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EarthquakeServiceImplTest {

    @Mock
    private EarthquakeRepository earthquakeRepository;

    @Mock
    private UsgsApiClient usgsApiClient;

    @InjectMocks
    private EarthquakeServiceImpl earthquakeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndStore_Success() {
        Earthquake e1 = Earthquake.builder().externalId("id1").magnitude(5.0).build();
        Earthquake e2 = Earthquake.builder().externalId("id2").magnitude(6.0).build();
        List<Earthquake> fetched = Arrays.asList(e1, e2);

        when(usgsApiClient.fetchEarthquakes()).thenReturn(fetched);
        when(earthquakeRepository.existsByExternalId("id1")).thenReturn(false);
        when(earthquakeRepository.existsByExternalId("id2")).thenReturn(true);
        when(earthquakeRepository.findAll()).thenReturn(fetched);

        List<Earthquake> result = earthquakeService.fetchAndStore();

        verify(earthquakeRepository, times(1)).save(e1);
        verify(earthquakeRepository, never()).save(e2);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAll() {
        List<Earthquake> earthquakes = Arrays.asList(new Earthquake(), new Earthquake());
        when(earthquakeRepository.findAll()).thenReturn(earthquakes);

        List<Earthquake> result = earthquakeService.getAll();

        assertEquals(2, result.size());
        verify(earthquakeRepository, times(1)).findAll();
    }

    @Test
    void testGetByMinMagnitude() {
        double minMag = 5.0;
        List<Earthquake> earthquakes = Arrays.asList(new Earthquake());
        when(earthquakeRepository.findByMagnitudeGreaterThan(minMag)).thenReturn(earthquakes);

        List<Earthquake> result = earthquakeService.getByMinMagnitude(minMag);

        assertEquals(1, result.size());
        verify(earthquakeRepository, times(1)).findByMagnitudeGreaterThan(minMag);
    }

    @Test
    void testDeleteById_Success() {
        Long id = 1L;
        when(earthquakeRepository.existsById(id)).thenReturn(true);

        earthquakeService.deleteById(id);

        verify(earthquakeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteById_NotFound() {
        Long id = 1L;
        when(earthquakeRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> earthquakeService.deleteById(id));
        verify(earthquakeRepository, never()).deleteById(id);
    }
}
