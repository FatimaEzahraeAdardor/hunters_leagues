package org.youcode.hunters_leagues.web.exception.user;

public class InvalidCredentialsException extends RuntimeException{
   public InvalidCredentialsException(String message){
        super(message);
    }
}
