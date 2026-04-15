package com.codeit.assignment.dto;

public record EarthquakeDto(
        Long id,
        Double magnitude,
        String magType,
        String place,
        String title,
        Long time
) {

}
