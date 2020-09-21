package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(rollbackFor = Exception.class)
    public Book addBook(String title, Author author, String genre){

        if(StringUtils.isAnyEmpty(title, genre) || author == null) {
            throw new IllegalArgumentException("Argument cannot be null or empty!");
        }

        Book bookToAdd = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        return bookRepository.save(bookToAdd);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int page, int size, String sortStrategy, String sortBy) {

        Sort.Direction sortDirection = sortStrategy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> getBooksByExample(int page,
                                        int size,
                                        String title,
                                        Author author,
                                        String genre,
                                        String sortStrategy,
                                        String sortBy) {

        Book book = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Book> exampleOfBook = Example.of(book, matcher);
        Sort.Direction sortDirection = sortStrategy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return bookRepository.findAll(exampleOfBook, pageable);
    }
}
