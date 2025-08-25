package com.example.demo.service;

import com.example.demo.model.Passenger;
import java.util.Optional;
import java.util.UUID;

public interface PassengerService {
    Passenger createPassenger(Passenger passenger);
    Optional<Passenger> getPassengerById(UUID id);
    Optional<Passenger> getPassengerByEmail(String email);
    Passenger authenticatePassenger(String email, String password);
    boolean logout(String email);
}
