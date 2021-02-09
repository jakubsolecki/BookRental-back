package pl.jsol.bookrental.model.entity;

import lombok.*;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
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

    public Loan(@NonNull LibraryMember libraryMember, @NonNull Book book) throws NoCopiesAvailableException {
        BookCopy bookedBookCopy;

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
