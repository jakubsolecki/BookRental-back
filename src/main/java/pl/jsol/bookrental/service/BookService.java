package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.exceptions.notFound.AuthorNotFoundException;
import pl.jsol.bookrental.exceptions.notFound.BookNotFoundException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Transactional(rollbackFor = Exception.class)
    public Book addBook(String title, Long authorId, String genre)
            throws IllegalArgumentException, EntityExistsException, AuthorNotFoundException {

        if(StringUtils.isAnyEmpty(title, genre) || authorId == null) {
            throw new IllegalArgumentException("Argument cannot be null or empty!");
        }

        Author author = authorService.findAuthorById(authorId).orElseThrow(() ->
                new AuthorNotFoundException(authorId));

        Book bookToAdd = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        Example<Book> bookExample = Example.of(bookToAdd);

        if (bookRepository.findAll(bookExample).isEmpty()) {
            return bookRepository.save(bookToAdd);
        }
        else {
            throw new EntityExistsException("This book already exists!");
        }
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int page, int size, String sortStrategy, String sortBy) {

        Sort.Direction sortDirection = (sortStrategy != null && sortStrategy.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> getBooksByExample(int page,
                                        int size,
                                        String title,
                                        Author author,
                                        String genre,
                                        String sort,
                                        String sortBy) {

        Book book = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        ExampleMatcher bookMatcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Book> exampleOfBook = Example.of(book, bookMatcher);
        Sort.Direction sortDirection = sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return bookRepository.findAll(exampleOfBook, pageable);
    }
}
