package com.example.routing.repositories;

import com.example.routing.models.MerchantConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantConfigRepo extends JpaRepository<MerchantConfiguration, Long> {
}
