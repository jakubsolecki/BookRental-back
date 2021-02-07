package pl.jsol.bookrental.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookTests {

    private Book book;
    private BookCopy bookCopy1;
    private BookCopy bookCopy2;

    @BeforeEach
    protected void setUp() {
        book = new Book("Title 1", Mockito.mock(Author.class), "fantasy");

        bookCopy1 = Mockito.mock(BookCopy.class);
        bookCopy2 = Mockito.mock(BookCopy.class);
    }

    @Test
    public void addCopy_whenAddCopy_thenReturnCorrectQuantity() {
        assertEquals(0, book.getCopiesQuantity());
        assertTrue(book.addCopy(bookCopy1));
        assertEquals(1, book.getCopiesQuantity());
    }

    @Test
    public void getNextAvailableCopy_whenAvailableCopy_thenReturnIt() {
        when(bookCopy1.isAvailable()).thenReturn(false);
        when(bookCopy2.isAvailable()).thenReturn(true);
        book.addCopy(bookCopy1);
        book.addCopy(bookCopy2);

        assertEquals(bookCopy2, book.getNextAvailableCopy());
    }

    @Test
    public void getNextAvailableCopy_whenNoCopyAvailable_thenExceptionThrown() {
        when(bookCopy1.isAvailable()).thenReturn(false);
        when(bookCopy2.isAvailable()).thenReturn(false);
        book.addCopy(bookCopy1);
        book.addCopy(bookCopy2);

        Assertions.assertThrows(NoCopiesAvailableException.class, () -> book.getNextAvailableCopy());
    }

    @Test
    public void getNextAvailableCopy_whenNoCopies_thenExceptionThrown() {
        Assertions.assertThrows(NoCopiesAvailableException.class, () -> book.getNextAvailableCopy());
    }

}
