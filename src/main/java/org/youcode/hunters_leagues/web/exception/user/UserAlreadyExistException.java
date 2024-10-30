package org.youcode.hunters_leagues.web.exception.user;

public class UserAlreadyExistException extends RuntimeException{
   public UserAlreadyExistException(String message){
        super(message);
    }
}
