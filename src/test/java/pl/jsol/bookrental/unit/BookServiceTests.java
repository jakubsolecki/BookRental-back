package pl.jsol.bookrental.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.service.AuthorService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceProtectedMethod extendedBookService;

    @Test
    public void addBook_whenNullOrEmptyParameter_thenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> extendedBookService.addBook("", 1L, "genre"));
        assertThrows(IllegalArgumentException.class, () -> extendedBookService.addBook("title", 1L, ""));

        assertThrows(NullPointerException.class, () -> extendedBookService.addBook("title", null, "genre"));
        assertThrows(NullPointerException.class, () -> extendedBookService.addBook("title", 1L, null));
        assertThrows(NullPointerException.class, () -> extendedBookService.addBook("", null, ""));
        assertThrows(NullPointerException.class, () -> extendedBookService.addBook("title", null, null));
        assertThrows(NullPointerException.class, () -> extendedBookService.addBook(null, null, null));
    }

    @Test
    public void addBook_whenCorrectInput_thenReturnAddedBook() {

        Book mockBook = Mockito.mock(Book.class);
        Author mockAuthor = Mockito.mock(Author.class);

        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        when(authorService.findAuthorById(1L)).thenReturn(mockAuthor);

        assertEquals(mockBook, extendedBookService.addBook("Title", 1L, "BookGenre"));
    }

    @Test
    public void addBook_whenBookExists_thenThrowException() {

        Book mockBook = Mockito.mock(Book.class);
        Author mockAuthor = Mockito.mock(Author.class);
        BookServiceProtectedMethod spiedExtendedBookService = Mockito.spy(extendedBookService);

        when(authorService.findAuthorById(any(Long.class))).thenReturn(mockAuthor);
        Mockito.doReturn(Optional.of(mockBook)).when(spiedExtendedBookService).verifyBookExistence(any(Book.class));

        assertThrows(ResourceAlreadyExistsException.class, () -> spiedExtendedBookService.addBook("Title", 1L, "genre"));
    }


}
