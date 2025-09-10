package com.example.demo.service;

import com.example.demo.helper.PasswordManager;
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

        String hashedPassword = PasswordManager.hashPassword(passenger.getPassword(),passenger.getEmail());
        passenger.setPassword(hashedPassword);
        return passengerRepository.save(passenger);
    }


    @Override
    public Passenger authenticatePassenger(String email, String password) {
        String hashedPassword = PasswordManager.hashPassword(password,email);
        Passenger passenger = passengerRepository.findByEmail(email)
            .filter(p -> p.getPassword().equals(hashedPassword))
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