package com.example.demo.repository;

import com.example.demo.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Optional<Passenger> findByEmail(String email);
    boolean existsByEmail(String email);
}
