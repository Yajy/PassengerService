package com.example.demo.exception;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler(PassengerException.class)
    public Status handlePassengerException(PassengerException e) {
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage());
    }

    @GrpcExceptionHandler(Exception.class)
    public Status handleException(Exception e) {
        return Status.INTERNAL.withDescription("An internal error occurred");
    }
}
