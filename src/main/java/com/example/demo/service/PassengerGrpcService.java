package com.example.demo.service;

import com.example.demo.exception.PassengerException;
import com.example.demo.grpc.*;
import com.example.demo.model.Passenger;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PassengerGrpcService extends PassengerServiceGrpc.PassengerServiceImplBase {

    @Autowired
    private PassengerService passengerService;

    @Override
    public void createPassenger(CreatePassengerRequest request, StreamObserver<CreatePassengerResponse> responseObserver) {
        Passenger passenger = Passenger.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .age(request.getAge())
                .status(0)  // default status for new passenger
                .build();

        passengerService.createPassenger(passenger);

        CreatePassengerResponse response = CreatePassengerResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void authenticatePassenger(AuthenticatePassengerRequest request, StreamObserver<AuthenticatePassengerResponse> responseObserver) {
        Passenger passenger = passengerService.authenticatePassenger(request.getEmail(), request.getPassword());

        AuthenticatePassengerResponse response = AuthenticatePassengerResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
        boolean success = passengerService.logout(request.getEmail());
        if (!success) {
            throw new PassengerException("Passenger not found");
        }

        LogoutResponse response = LogoutResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
