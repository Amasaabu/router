package com.example.routing.repositories;

import com.example.routing.models.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {
    public Optional<ServiceUser> findByUserId(String userId);
}
