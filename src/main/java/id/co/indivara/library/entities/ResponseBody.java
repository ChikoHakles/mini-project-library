package id.co.indivara.library.entities;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ResponseBody<T> {
    private final Date timestamp = new Date();
    private int status;
    private String message;
    @Singular private List<String> errors;
    private T data;
}
