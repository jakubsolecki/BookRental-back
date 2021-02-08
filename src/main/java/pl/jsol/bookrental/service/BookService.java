package pl.jsol.bookrental.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.IBookCopyRepository;
import pl.jsol.bookrental.dal.repository.IBookRepository;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    protected final IBookRepository iBookRepository;
    private final IBookCopyRepository iBookCopyRepository;
    private final AuthorService authorService;

    @Transactional(rollbackFor = Exception.class)
    public Book addBook(String title, Long authorId, String genre)
            throws IllegalArgumentException, ResourceAlreadyExistsException, EntityNotFoundException {

        if(StringUtils.isAnyEmpty(title, genre) || authorId == null) {
            throw new IllegalArgumentException("Parameters cannot be null nor empty.");
        }

        Author author = authorService.findAuthorById(authorId);

        Book bookToAdd = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        Optional<Book> foundBook = verifyBookExistence(bookToAdd);

        if (foundBook.isEmpty()) {
            return iBookRepository.save(bookToAdd);
        }
        else {
            throw new ResourceAlreadyExistsException(foundBook.get());
        }
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long bookId) throws EntityNotFoundException {
        return iBookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book", bookId));
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int page, int size, String sort, String sortBy) {

        Sort.Direction sortDirection = SortParser.parseSortFromString(sort);
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return iBookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> getBooksByExample(
            int page,
            int size,
            String title,
            Author author,
            String genre,
            String sort,
            String sortBy
    ) {

        Book book = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        Sort.Direction sortDirection = SortParser.parseSortFromString(sort);

        ExampleMatcher bookMatcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Book> exampleOfBook = Example.of(book, bookMatcher);
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return iBookRepository.findAll(exampleOfBook, pageable);
    }

    @Transactional(readOnly = true)
    public List<BookCopy> getBookCopies(@NonNull Long bookId) throws EntityNotFoundException {

        Book book = getBookById(bookId);

        if (book.getCopiesQuantity() == 0) {
            throw new NoCopiesAvailableException("This book has no copies");
        } else {
            return book.getCopies();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BookCopy addBookCopy(@NonNull Long bookId) throws EntityNotFoundException {

        Book book = getBookById(bookId);
        return iBookCopyRepository.save(new BookCopy(book));
    }

    @Transactional(readOnly = true)
    public BookCopy getBookCopyById(@NonNull Long id) {
        return iBookCopyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Copy", id));
    }

    @Transactional(readOnly = true)
    protected Optional<Book> verifyBookExistence(@NonNull Book book) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Book> bookExample = Example.of(book, exampleMatcher);

        return iBookRepository.findOne(bookExample);
    }
}
