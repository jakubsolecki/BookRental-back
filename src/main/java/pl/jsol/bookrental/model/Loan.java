package pl.jsol.bookrental.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NonNull
    private Member member;

    @ManyToOne
    @NonNull
    private Book book;

    @NonNull
    private LocalDate outDate;

    @NonNull
    private LocalDate dueDate;

    public void extendExpiryDate() {
        dueDate = dueDate.plusDays(7);
    }
}
