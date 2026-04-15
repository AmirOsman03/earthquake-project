package com.codeit.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "earthquakes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Earthquake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    private Double magnitude;

    private String magType;

    private String place;

    private String title;

    private Long time;

}
