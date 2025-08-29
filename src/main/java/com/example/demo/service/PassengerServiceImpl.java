package com.example.demo.service;

import com.example.demo.model.Passenger;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.exception.PassengerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger createPassenger(Passenger passenger) {
        if (passengerRepository.findByEmail(passenger.getEmail()).isPresent()) {
            throw new PassengerException("Email already exists");
        }
        return passengerRepository.save(passenger);
    }


    @Override
    public Passenger authenticatePassenger(String email, String password) {
        Passenger passenger = passengerRepository.findByEmail(email)
            .filter(p -> p.getPassword().equals(password))
            .orElseThrow(() -> new PassengerException("Invalid email or password"));

        // Update status to 1 (logged in)
        passenger.setStatus(1);
        return passengerRepository.save(passenger);
    }

    @Override
    public boolean logout(String email) {

        return passengerRepository.findByEmail(email)
                .map(passenger -> {
                    if (passenger.getStatus() == 0) {
                        throw new PassengerException("Passenger is already logged out");
                    }
                    passenger.setStatus(0);
                    passengerRepository.save(passenger);
                    return true;
                }).orElse(false);
    }


}