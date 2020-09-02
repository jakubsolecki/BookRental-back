package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    //TODO: remove optional. Add defaults in controller
    public Page<Book> getBooks(
            Optional<Integer> page,
            Optional<Integer> quantity,
            Optional<Boolean> ascending,
            Optional<String> sortBy,
            Optional<String> title,
            Optional<String> author,
            Optional<Genre> genre
    ) {
        Book exampleBook = Book
                .builder()
                .title(title.orElse(null))
                .author(author.orElse(null))
                .genre(genre.orElse(null))
                .build();

        return bookRepository.findAll(Example.of(exampleBook),
                PageRequest.of(page.orElse(0),
                        quantity.orElse(4),
                        ascending.orElse(true) ?
                            Sort.by(sortBy.orElse("title")).ascending().and(Sort.by("title")) :
                            Sort.by(sortBy.orElse("title")).descending().and(Sort.by("title"))
                )
        );
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        Book titledBook = Book.builder().title(title).build();
        return bookRepository.findOne(Example.of(titledBook));
    }

}
