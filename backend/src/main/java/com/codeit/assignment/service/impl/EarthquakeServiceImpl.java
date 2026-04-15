package com.codeit.assignment.service.impl;

import com.codeit.assignment.model.Earthquake;
import com.codeit.assignment.repository.EarthquakeRepository;
import com.codeit.assignment.service.EarthquakeService;
import com.codeit.assignment.service.UsgsApiClient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarthquakeServiceImpl implements EarthquakeService {

    private final EarthquakeRepository earthquakeRepository;
    private final UsgsApiClient usgsApiClient;

    public EarthquakeServiceImpl(EarthquakeRepository earthquakeRepository, UsgsApiClient usgsApiClient) {
        this.earthquakeRepository = earthquakeRepository;
        this.usgsApiClient = usgsApiClient;
    }

    @Override
    @Transactional
    public List<Earthquake> fetchAndStore() {
        List<Earthquake> fetched = usgsApiClient.fetchEarthquakes();

        for (Earthquake e : fetched) {
            if (!earthquakeRepository.existsByExternalId(e.getExternalId())) {
                earthquakeRepository.save(e);
            }
        }

        return earthquakeRepository.findAll();
    }

    @Override
    public List<Earthquake> getAll() {
        return earthquakeRepository.findAll();
    }

    @Override
    public List<Earthquake> getByMinMagnitude(double minMagnitude) {
        return earthquakeRepository.findByMagnitudeGreaterThan(minMagnitude);
    }

    @Override
    public List<Earthquake> getAfterTime(Long timestamp) {
        return earthquakeRepository.findByTimeAfter(timestamp);
    }

    @Override
    public void deleteById(Long id) {
        if (!earthquakeRepository.existsById(id)) {
            throw new RuntimeException("Earthquake with id " + id + " not found");
        }
        earthquakeRepository.deleteById(id);
    }

}
