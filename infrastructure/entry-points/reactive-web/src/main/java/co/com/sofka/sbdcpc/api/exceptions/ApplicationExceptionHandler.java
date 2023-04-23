package co.com.sofka.sbdcpc.api.exceptions;

import co.com.sofka.sbdcpc.model.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleSbdcpcAPIException(ApplicationException sbdcpcAPIException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message", sbdcpcAPIException.getMessage());
        errorMap.put("status", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.ok(errorMap);
    }
}