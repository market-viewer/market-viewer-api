package jotalac.market_viewer.market_viewer_app.handler;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jotalac.market_viewer.market_viewer_app.dto.ErrorResponse;
import jotalac.market_viewer.market_viewer_app.exception.AlreadyExistsException;
import jotalac.market_viewer.market_viewer_app.exception.NotFoundException;
import jotalac.market_viewer.market_viewer_app.exception.device.DeviceException;
import jotalac.market_viewer.market_viewer_app.exception.screen.ScreenException;
import jotalac.market_viewer.market_viewer_app.exception.user.UserException;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse persistenceException(HttpServletRequest request, PersistenceException ex) {
        return new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFound(HttpServletRequest request, NotFoundException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFound(HttpServletRequest request, NoResourceFoundException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStateException(HttpServletRequest request, IllegalStateException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userException(HttpServletRequest request, UserException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DeviceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse deviceException(HttpServletRequest request, DeviceException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ScreenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse screenException(HttpServletRequest request, ScreenException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse alreadyExistsException(HttpServletRequest request, AlreadyExistsException e) {
        return new ErrorResponse(LocalDateTime.now(), e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingRequestBodyException(HttpServletRequest request, HttpMessageNotReadableException e) {
        return new ErrorResponse(LocalDateTime.now(), "Request body invalid", request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(HttpServletRequest request, MethodArgumentNotValidException e) {
        // Instead of .toString(), consider passing the Map directly
        // if your ErrorResponse 'message' field is an Object
        String detail = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", "));

        return new ErrorResponse(LocalDateTime.now(), "Validation failed: " + detail, request.getRequestURI());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleAllUnhandledExceptions(HttpServletRequest request, Exception e) {
//        // Log the actual error on the server so you can debug it
//        // logger.error("Unhandled exception: ", e);
//
//        // Return a generic response with NO message from the exception
//        return new ErrorResponse(
//                LocalDateTime.now(),
//                "An unexpected error occurred", // Or just leave empty ""
//                request.getRequestURI()
//        );
//    }

}
