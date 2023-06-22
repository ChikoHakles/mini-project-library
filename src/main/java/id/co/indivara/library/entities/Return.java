package id.co.indivara.library.entities;

import id.co.indivara.library.utils.TransactionType;
import id.co.indivara.library.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MST_return")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Return {
    @Id
    @Column(name = "return_id")
    private UUID returnId;

    @Column(name = "return_code")
    private String returnCode;

    @OneToOne
    @JoinColumn(name = "borrow")
    private Borrow borrow;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "return_is_late")
    private Boolean returnIsLate;

    @Column(name = "return_penalty")
    private Integer returnPenalty;

    public Return(Borrow borrow) {
        this.returnId = UUID.randomUUID();
        this.returnDate = LocalDateTime.now();
        this.returnCode = Utility.generateCode(TransactionType.RETURN, returnDate);
        this.borrow = borrow;
        this.returnIsLate = returnDate.isAfter(borrow.getReturnDate().atTime(23, 59, 59));
        this.returnPenalty = this.returnIsLate ? 100000 : null;
    }
}