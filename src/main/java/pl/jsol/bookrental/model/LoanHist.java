package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor
@Getter
public class LoanHist extends Loan{
    @NonNull
    private LocalDate inDate;

    @NonNull
    private BigDecimal penalty;

    @Setter
    private String comments;

    public LoanHist(Loan loan) {
        member = loan.getMember();
        copy = loan.getCopy();
        dueDate = loan.getDueDate();
        inDate = LocalDate.now();
        Period diff = Period.between(dueDate, inDate);
        penalty = diff.isNegative() ? new BigDecimal(0) : new BigDecimal(diff.getDays());
        loan.returnCopy();
    }

    @Override
    public void returnCopy() {}

    @Override
    public void extendExpiryDate() {}
}
