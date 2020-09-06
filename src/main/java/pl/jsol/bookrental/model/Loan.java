package pl.jsol.bookrental.model;

import lombok.*;

import javax.naming.CannotProceedException;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    @NonNull
    protected Member member;

    @OneToOne
    @NonNull
    protected Copy copy;

    @NonNull
    private LocalDate outDate;

    @NonNull
    protected LocalDate dueDate;

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

    public void returnCopy() {
        copy.setAvailable(true);
    }

    public void extendExpiryDate() {
        dueDate = dueDate.plusDays(7);
    }
}
