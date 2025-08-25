package com.example.demo.service;

import com.example.demo.model.Passenger;

public interface PassengerService {
    Passenger createPassenger(Passenger passenger);
    Passenger authenticatePassenger(String email, String password);
    boolean logout(String email);
}
