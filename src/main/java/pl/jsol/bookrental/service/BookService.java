package pl.jsol.bookrental.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.IBookRepository;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    protected final IBookRepository IBookRepository;
    private final AuthorService authorService;

    @Transactional(rollbackFor = Exception.class)
    public Book addBook(@NonNull String title, @NonNull Long authorId, @NonNull String genre)
            throws IllegalArgumentException, ResourceAlreadyExistsException, EntityNotFoundException {

        if(StringUtils.isAnyEmpty(title, genre) || authorId == null) {
            throw new IllegalArgumentException("Argument cannot be null or empty.");
        }

        Author author = authorService.findAuthorById(authorId);

        Book bookToAdd = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        Optional<Book> foundBook = verifyBookExistence(bookToAdd);

        if (foundBook.isEmpty()) {
            return IBookRepository.save(bookToAdd);
        }
        else {
            throw new ResourceAlreadyExistsException(foundBook.get());
        }
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long bookId) throws EntityNotFoundException {
        return IBookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book", bookId));
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int page, int size, String sort, String sortBy) {

        Sort.Direction sortDirection = (sort != null && sort.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return IBookRepository.findAll(pageable);
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

        return IBookRepository.findAll(exampleOfBook, pageable);
    }

    @Transactional(readOnly = true)
    protected Optional<Book> verifyBookExistence(Book book) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Book> bookExample = Example.of(book, exampleMatcher);

        return IBookRepository.findOne(bookExample);
    }
}
