package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
public class LoanHist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @NonNull
    private Member member;

    @ManyToOne
    @NonNull
    private Copy copy;

    @NonNull
    private LocalDate dueDate;

    @NonNull
    private LocalDate inDate;

    private BigDecimal penalty;

    private String comments;

    public LoanHist(Loan loan) {
        member = loan.getMember();
        copy = loan.getCopy();
        dueDate = loan.getDueDate();
        inDate = LocalDate.now();
        Period diff = Period.between(inDate, dueDate);
        penalty = diff.isNegative() ? new BigDecimal(diff.getDays()) : new BigDecimal(0);
        loan.setCopyAvailability(true);
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
