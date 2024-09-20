package com.example.routing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(indexes =
        {@Index(name = "userId", columnList = "userId")})
public class ServiceUser {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String userId;
    private String password;
}
