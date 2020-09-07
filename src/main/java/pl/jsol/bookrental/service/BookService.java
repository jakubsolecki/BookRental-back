package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Genre;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(String title, String author, String genre){

        if(StringUtils.isAnyEmpty(title, author, genre)) {
            throw new IllegalArgumentException("Argument cannot be null or empty string!");
        }

        Book bookToAdd = new Book(title, author, Genre.parseGenre(genre));

        return bookRepository.save(bookToAdd);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> getAllBooks(int page, int size, String sort, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return bookRepository.findAll(pageable);
    }

//    public Page<Book> findBooks(String title,
//                                String author,
//                                String genre,
//                                int size,
//                                int page,
//                                String sortStrategy,
//                                String sortBy) {
//
//        ExampleMatcher bookMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
//
//        bookMatcher = title.equals("") ? bookMatcher = bookMatcher.withIgnorePaths("title") : bookMatcher.withMatcher("title", startsWith());
//        bookMatcher = author.equals("") ? bookMatcher = bookMatcher.withIgnorePaths("author") : bookMatcher.withMatcher("author", startsWith());
//        bookMatcher = genre.equals("") ? bookMatcher = bookMatcher.withIgnorePaths("genre") : bookMatcher.withMatcher("genre", exact());
//
//        Book book  = new Book(title, author, Genre.parseGenre(genre));
//        Example<Book> example = Example.of((book), bookMatcher);
//        Pageable pageable = PageRequest.of(page, size,
//                sortStrategy.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
//
//        return bookRepository.findAll(example, pageable);
//    }


}
