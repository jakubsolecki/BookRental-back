package pl.jsol.bookrental.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor
@Data
public class LoanHist extends Loan {
    @NonNull
    private LocalDate inDate;

    @NonNull
    private BigDecimal penalty;

    private String comments;

    public LoanHist(@NonNull Loan loan) {
        libraryMember = loan.getLibraryMember();
        bookCopy = loan.getBookCopy();
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
