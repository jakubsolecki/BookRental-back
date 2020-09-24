package pl.jsol.bookrental.model;

import lombok.*;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;

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
    @JoinColumn
    protected LibraryMember libraryMember;

    @OneToOne
    @NonNull
    @JoinColumn
    protected BookCopy bookCopy;

    @NonNull
    private LocalDate outDate;

    @NonNull
    protected LocalDate dueDate;

    public Loan(LibraryMember libraryMember, Book book) throws NoCopiesAvailableException {
        BookCopy bookedBookCopy = null;

        try {
            bookedBookCopy = book.getNextAvailableCopy();
        } catch (NoCopiesAvailableException error) {
            throw new NoCopiesAvailableException(error.getMessage());
        }

        bookedBookCopy.setAvailable(false);
        this.libraryMember = libraryMember;
        bookCopy = bookedBookCopy;
        outDate = LocalDate.now();
        dueDate = outDate.plusDays(7);
    }

    public void returnCopy() {
        bookCopy.setAvailable(true);
    }

    public void extendExpiryDate() {
        dueDate = dueDate.plusDays(7);
    }
}
