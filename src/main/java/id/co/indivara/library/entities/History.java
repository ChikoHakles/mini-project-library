package id.co.indivara.library.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer bookId;
    private String bookTitle;
    private Integer readerId;
    private String readerName;
    private String borrowCode;
    private LocalDateTime borrowDate;
    private String returnCode;
    private LocalDateTime returnDate;
}
