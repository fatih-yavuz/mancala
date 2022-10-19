package com.fatih.controller;

import com.fatih.exception.GameNotFoundException;
import com.fatih.exception.IllegalTurnException;
import com.fatih.model.Error;
import com.fatih.model.ErrorDetail;
import org.greenrobot.eventbus.EventBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.fatih.exception.ExceptionUtils.getExceptionClass;
import static com.fatih.exception.ExceptionUtils.getHttpStatusFromMessage;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(final Exception exception) {
        final ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setKey("mancala.generic.error");
        errorDetail.setMessage(exception.getMessage());

        final Error error = new Error();
        error.setException("GenericException");
        error.addErrorDetail(errorDetail);

        LOGGER.error("HTTP_STATUS: {} ,Generic Exception Occurred.", INTERNAL_SERVER_ERROR, exception);

        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Error> handleGameNotFoundException(final GameNotFoundException exception) {
        LOGGER.error("HTTP_STATUS: {} ,GameNotFoundException Occurred.", NOT_FOUND, exception);

        final Error error = new Error();
        final ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setKey("com.fatih.mancala.GameNotFoundException");
        errorDetail.setMessage(exception.getMessage());
        error.addErrorDetail(errorDetail);
        error.setException("GameNotFoundException");
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(IllegalTurnException.class)
    public ResponseEntity<Error> handleIllegalTurnException(final IllegalTurnException exception) {
        LOGGER.error("HTTP_STATUS: {} ,IllegalTurnException Occurred.", CONFLICT, exception);

        final Error error = new Error();
        final ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setKey("com.bol.mancala.IllegalTurnException");
        errorDetail.setMessage(exception.getMessage());
        error.addErrorDetail(errorDetail);
        error.setException("IllegalTurnException");
        return new ResponseEntity<>(error, CONFLICT);

    }

    @ExceptionHandler(EventBusException.class)
    public ResponseEntity<Error> handleEventBusExceptionException(final Throwable exception) {
        final Error error = new Error();
        error.setException(getExceptionClass(exception));
        LOGGER.error("HTTP_STATUS: {} , Cause: {}.", exception.getMessage(), exception.getCause());
        try {
            final HttpStatus status = getHttpStatusFromMessage(exception);
            return new ResponseEntity<>(error, status);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
        }


    }

}
