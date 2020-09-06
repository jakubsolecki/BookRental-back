package pl.jsol.bookrental.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Copy;
import pl.jsol.bookrental.model.Genre;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookTests {

    private Book book;
    private Copy copy1;
    private Copy copy2;

    @BeforeEach
    protected void setUp() {
        book = Book.builder()
                .title("Title 1")
                .author("Author 1")
                .genre(Genre.parseGenre("fantasy"))
                .build();

        copy1 = Mockito.mock(Copy.class);
        copy2 = Mockito.mock(Copy.class);

    }

    @Test
    public void addCopyTest() {

        // when then
        assertEquals(0, book.getCopiesQuantity());
        assertTrue(book.addCopy(copy1));
        assertEquals(1, book.getCopiesQuantity());
    }

    @Test
    public void getNextAvailableCopyTest() {

        when(copy1.isAvailable()).thenReturn(false);
        when(copy2.isAvailable()).thenReturn(true);
        book.addCopy(copy1);
        book.addCopy(copy2);

        assertEquals(copy2, book.getNextAvailableCopy());
    }

    @Test
    public void getNextAvailableCopy_NoneAvailable_ExceptionThrown() {

        when(copy1.isAvailable()).thenReturn(false);
        when(copy2.isAvailable()).thenReturn(false);
        book.addCopy(copy1);
        book.addCopy(copy2);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            book.getNextAvailableCopy();
        });
    }

    @Test
    public void getNextAvailableCopy_NoCopies_ExceptionThrown() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            book.getNextAvailableCopy();
        });
    }

}
