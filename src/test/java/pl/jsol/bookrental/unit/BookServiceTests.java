package pl.jsol.bookrental.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void addBook_whenNullOrEmptyParameter_thenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("", Mockito.mock(Long.class), "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", null, "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", Mockito.mock(Long.class), null));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("", null, "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", null, null));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(null, null, null));
    }

    @Test
    public void addBook_whenCorrectInput_thenReturnAddedBook() {

        Book mockBook = Mockito.mock(Book.class);

        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        assertEquals(mockBook, bookService.addBook("Title", Mockito.mock(Long.class), "BookGenre"));
    }
}
