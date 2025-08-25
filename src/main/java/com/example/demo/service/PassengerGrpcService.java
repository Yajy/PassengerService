package com.example.demo.service;

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
        try {
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
        } catch (Exception e) {
            CreatePassengerResponse response = CreatePassengerResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void authenticatePassenger(AuthenticatePassengerRequest request, StreamObserver<AuthenticatePassengerResponse> responseObserver) {
        try {
            passengerService.authenticatePassenger(request.getEmail(), request.getPassword());

            AuthenticatePassengerResponse response = AuthenticatePassengerResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            AuthenticatePassengerResponse response = AuthenticatePassengerResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void logoutPassenger(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
        try {
            boolean success = passengerService.logout(request.getEmail());

            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(success)
                    .setErrorMessage(success ? "" : "Logout failed - user not found")
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
