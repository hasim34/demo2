package com.demov2.collab.Exception;


public class NoUserFoundException extends RuntimeException{
    public NoUserFoundException(){}
    public NoUserFoundException(String message){
        super(message);
    }
}
