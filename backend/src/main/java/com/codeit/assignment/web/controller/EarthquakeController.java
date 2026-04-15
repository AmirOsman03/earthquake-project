package com.codeit.assignment.web.controller;

import com.codeit.assignment.model.Earthquake;
import com.codeit.assignment.service.EarthquakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/earthquakes")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<Earthquake>> fetchAndStore() {
        return ResponseEntity.ok(earthquakeService.fetchAndStore());
    }

    @GetMapping
    public ResponseEntity<List<Earthquake>> getAll() {
        return ResponseEntity.ok(earthquakeService.getAll());
    }

    @GetMapping("/filter/magnitude")
    public ResponseEntity<List<Earthquake>> filterByMagnitude(@RequestParam double minMagnitude) {
        return ResponseEntity.ok(earthquakeService.getByMinMagnitude(minMagnitude));
    }

    @GetMapping("/filter/time")
    public ResponseEntity<List<Earthquake>> filterByTime(@RequestParam long timestamp) {
        return ResponseEntity.ok(earthquakeService.getAfterTime(timestamp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        earthquakeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
