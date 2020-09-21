package pl.jsol.bookrental.service;

import com.sun.istack.NotNull;
import lombok.NonNull;
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
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(String title, Author author, String genre){

        if(StringUtils.isAnyEmpty(title, genre) || author == null) {
            throw new IllegalArgumentException("Argument cannot be null or empty string!");
        }

        Book bookToAdd = new Book(title, author, genre);

        return bookRepository.save(bookToAdd);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> getAllBooks(int page, int size, String sort, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return bookRepository.findAll(pageable);
    }
}
