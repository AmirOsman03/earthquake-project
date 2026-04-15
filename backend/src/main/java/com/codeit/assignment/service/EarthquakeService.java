package com.codeit.assignment.service;

import com.codeit.assignment.model.Earthquake;

import java.util.List;

public interface EarthquakeService {

    List<Earthquake> fetchAndStore();

    List<Earthquake> getAll();

    List<Earthquake> getByMinMagnitude(double minMagnitude);

    List<Earthquake> getAfterTime(Long timestamp);

    void deleteById(Long id);

}
