package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;
import pl.jsol.bookrental.model.DataSchema;
import pl.jsol.bookrental.service.BookCopyService;
import pl.jsol.bookrental.service.BookService;

import java.net.URI;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookCopyService bookCopyService;

    @GetMapping
    public Page<Book> getAllBooks (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "title") String sortBy) {

        return bookService.getAllBooks(page, size, sort, sortBy);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {

        Book book = bookService.getBookById(id);
        Link selfLink = linkTo(BookController.class).slash(book.getId()).withSelfRel();

        return book.add(selfLink);
    }

    @GetMapping("/copies")
    public Set<BookCopy> getCopiesOfBook(Long bookId) {

        try {
            return bookService.getBookById(bookId).getCopies();
        } catch (NoCopiesAvailableException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book postBook(
            @RequestParam String title,
            @RequestParam Long authorId,
            @RequestParam String genre) {

        try {
            Book book = bookService.addBook(title, authorId, genre);
            Link selfLink = linkTo(BookController.class).slash(book.getId()).withSelfRel();
            return book.add(selfLink);
        } catch (ResourceAlreadyExistsException ex) {
            DataSchema<?> existingBook = ex.getExistingEntity();
            URI selfLink = linkTo(BookController.class).slash(existingBook.getId()).toUri();

            throw new ResponseStatusException(HttpStatus.SEE_OTHER, selfLink.toString(), ex);
        }
    }


}
