package org.youcode.hunters_leagues.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.youcode.hunters_leagues.web.exception.competition.CompetitionDateException;
import org.youcode.hunters_leagues.web.exception.competition.InvalidCompetitionExeption;
import org.youcode.hunters_leagues.web.exception.competition.InvalidRegistrationException;
import org.youcode.hunters_leagues.web.exception.participation.ParticipationAlreadyExistsException;
import org.youcode.hunters_leagues.web.exception.participation.ParticipationInvalidException;
import org.youcode.hunters_leagues.web.exception.species.InvalidSpeciesExeption;
import org.youcode.hunters_leagues.web.exception.species.InvalidWheightExeption;
import org.youcode.hunters_leagues.web.exception.user.InvalidCredentialsException;
import org.youcode.hunters_leagues.web.exception.user.InvalidUserExeption;
import org.youcode.hunters_leagues.web.exception.user.UserAlreadyExistException;
import org.youcode.hunters_leagues.web.exception.user.UserLicenseExpirationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserExeption.class)
    public ResponseEntity<String> handleInvalidUserException(InvalidUserExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidSpeciesExeption.class)
    public ResponseEntity<String> handleInvalidSpeciesExeption(InvalidSpeciesExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCompetitionExeption.class)
    public ResponseEntity<String> handleInvalidCompetitionExeption(InvalidCompetitionExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidRegistrationException.class)
    public ResponseEntity<String> handleInvalidRegistrationException(InvalidRegistrationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CompetitionDateException.class)
    public ResponseEntity<String> handleCompetitionDateException(CompetitionDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ParticipationAlreadyExistsException.class)
    public ResponseEntity<String> handleParticipationAlreadyExistsException(ParticipationAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ParticipationInvalidException.class)
    public ResponseEntity<String> handlePParticipationInvalidException(ParticipationInvalidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserLicenseExpirationException.class)
    public ResponseEntity<String> handleUserLicenseExpirationException(UserLicenseExpirationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidWheightExeption.class)
    public ResponseEntity<String> handleInvalidWheightExeption(InvalidWheightExeption ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HuntersLeagueException.class)
    public ResponseEntity<String> handleHuntersLeagueException(HuntersLeagueException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }




}
