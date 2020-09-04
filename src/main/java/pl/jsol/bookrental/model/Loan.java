package pl.jsol.bookrental.model;

import lombok.*;

import javax.naming.CannotProceedException;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Entity
@Data
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NonNull
    private Member member;

    @OneToOne
    @NonNull
    private Copy copy;

    @NonNull
    private LocalDate outDate;

    @NonNull
    private LocalDate dueDate;

    public Loan(Member member, Book book) throws CannotProceedException {
        Copy bookedCopy = null;

        try {
            bookedCopy = book.getNextAvailableCopy();
        } catch (NoSuchElementException error) {
            throw new CannotProceedException(error.getMessage());
        }

        bookedCopy.setAvailable(false);
        this.member = member;
        copy = bookedCopy;
        outDate = LocalDate.now();
        dueDate = outDate.plusDays(7);
    }

    public void setCopyAvailability(boolean availability) {
        copy.setAvailable(availability);
    }

    public void extendExpiryDate() {
        dueDate = dueDate.plusDays(7);
    }
}
