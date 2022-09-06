package com.ciandt.summit.bootcamp2022.services.exceptions;

public class MusicAlreadyExistException extends RuntimeException{

    public MusicAlreadyExistException(String msg) {
        super(msg);
    }
}
