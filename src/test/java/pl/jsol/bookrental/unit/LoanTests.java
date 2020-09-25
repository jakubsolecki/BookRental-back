package pl.jsol.bookrental.unit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.LibraryMember;

import javax.naming.CannotProceedException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class LoanTests {

    @Test
    public void newLoan_whenBookWithoutCopies_thenExceptionThrown() {
        Book book = Mockito.mock(Book.class);
        LibraryMember libraryMember = Mockito.mock(LibraryMember.class);

        when(book.getNextAvailableCopy()).thenThrow(new NoSuchElementException("No copies available!"));

        assertThrows(NoSuchElementException.class, () -> new Loan(libraryMember, book));
    }

}
