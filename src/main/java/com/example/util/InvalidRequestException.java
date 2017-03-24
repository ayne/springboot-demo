package com.example.util;

import org.hibernate.service.spi.ServiceException;

/**
 * Created by charmanesantiago on 02/03/2016.
 */
public class InvalidRequestException extends ServiceException {

    public InvalidRequestException(){
        super("Invalid Request");
    }

    public InvalidRequestException(String message){
        super(message);
    }
}