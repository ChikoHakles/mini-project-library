package id.co.indivara.library.exceptions;

import id.co.indivara.library.entities.ResponseBody;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseBody.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ResponseBody<Object>> HttpMessageNotReadableHandler(HttpMessageNotReadableException ex) {
        String [] errorSplited = ex.getMessage().split("; ");
        ArrayList<String> errors = new ArrayList<>(Arrays.asList(errorSplited));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseBody.builder()
                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Make sure your request follow the instructed rules")
                        .errors(errors)
                        .build()
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
        return ResponseEntity.internalServerError().body(
                ResponseBody.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(ex.getMessage())
                        .build()
        );
    }
}
