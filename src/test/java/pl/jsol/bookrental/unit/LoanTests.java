package pl.jsol.bookrental.unit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.Member;

import javax.naming.CannotProceedException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class LoanTests {

    @Test
    public void newLoan_BookWithoutCopies_ExceptionThrown() {
        Book book = Mockito.mock(Book.class);
        Member member = Mockito.mock(Member.class);

        when(book.getNextAvailableCopy()).thenThrow(new NoSuchElementException("No copies available!"));

        assertThrows(CannotProceedException.class, () -> new Loan(member, book));
    }

}
