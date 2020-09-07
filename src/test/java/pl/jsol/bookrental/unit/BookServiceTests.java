package pl.jsol.bookrental.unit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.service.BookService;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(MockitoJUnitRunner.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void addBook_whenNullOrEmptyParameter_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("", "author", "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", null, "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", "author", null));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("", "", "genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("title", null, null));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(null, null, null));
    }
}
