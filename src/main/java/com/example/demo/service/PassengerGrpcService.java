package com.example.demo.service;

import com.example.demo.exception.PassengerException;
import com.example.demo.model.Passenger;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import net.devh.boot.grpc.server.service.GrpcService;

import com.example.demo.grpc.PassengerServiceGrpc;
import com.example.demo.grpc.CreatePassengerRequest;
import com.example.demo.grpc.CreatePassengerResponse;
import com.example.demo.grpc.AuthenticatePassengerRequest;
import com.example.demo.grpc.AuthenticatePassengerResponse;
import com.example.demo.grpc.LogoutRequest;
import com.example.demo.grpc.LogoutResponse;

@GrpcService
public class PassengerGrpcService extends PassengerServiceGrpc.PassengerServiceImplBase {

    private final PassengerService passengerService;

    @Autowired
    public PassengerGrpcService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

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
                    .status(0)
                    .build();

            passengerService.createPassenger(passenger);

            CreatePassengerResponse response = CreatePassengerResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PassengerException e) {
            CreatePassengerResponse response = CreatePassengerResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void authenticatePassenger(AuthenticatePassengerRequest request, StreamObserver<AuthenticatePassengerResponse> responseObserver) {
        try {
            Passenger passenger = passengerService.authenticatePassenger(request.getEmail(), request.getPassword());

            AuthenticatePassengerResponse response = AuthenticatePassengerResponse.newBuilder()
                    .setSuccess(passenger != null)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PassengerException e) {
            AuthenticatePassengerResponse response = AuthenticatePassengerResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void logoutPassenger(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
        try {
            boolean success = passengerService.logout(request.getEmail());

            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(success)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (PassengerException e) {
            LogoutResponse response = LogoutResponse.newBuilder()
                    .setSuccess(false)
                    .setErrorMessage(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
