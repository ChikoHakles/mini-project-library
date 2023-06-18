package id.co.indivara.perpustakaan.exceptions;

import id.co.indivara.perpustakaan.entities.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler{
    @ExceptionHandler(DataRelatedException.class)
    ResponseEntity<ResponseBody<Object>> dataRelatedHandler(DataRelatedException ex) {
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null, null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ResponseBody<Object>> HttpMessageNotReadableHandler(HttpMessageNotReadableException ex) {
        String [] errorSplited = ex.getMessage().split("; ");
        ArrayList<String> errors = new ArrayList<>(Arrays.asList(errorSplited));
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.NOT_ACCEPTABLE.value(), "Make sure your request follow the instructed rules", errors, null),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseBody<Object>> validationHandler(MethodArgumentNotValidException ex) {
        ArrayList<String> errors = (ArrayList<String>) ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ResponseBody.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data Not Valid")
                        .errors(errors)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ResponseBody<Object>> ExceptionHandler(Exception ex) {
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null, null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
