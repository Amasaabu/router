package com.example.routing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(indexes =
        {@Index(name = "name", columnList = "name"), @Index(name = "status", columnList = "status")})
public class Provider {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String status;
}