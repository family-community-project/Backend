package com.example.family.exception;

public class AlreadyJoinedFamilyException extends RuntimeException{
    public AlreadyJoinedFamilyException(String message) {
        super(message);
    }
}
