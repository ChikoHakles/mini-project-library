package id.co.indivara.perpustakaan.exceptions;

import id.co.indivara.perpustakaan.entities.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler{
    @ExceptionHandler(DataNotFoundException.class)
    ResponseEntity<ResponseBody<Object>> dataNotFoundHandler(DataNotFoundException ex) {
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.NOT_FOUND.value(), "No Data", null, null),
                HttpStatus.NOT_FOUND
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
}
