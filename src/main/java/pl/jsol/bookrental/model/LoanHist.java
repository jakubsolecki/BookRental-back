package pl.jsol.bookrental.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class LoanHist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @NonNull
    private Member member;

    @ManyToOne
    @NonNull
    private Book book;

    @NonNull
    private LocalDate dueDate;

    @NonNull
    private LocalDate inDate;

    private BigDecimal penalty;

    private String comments;

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
