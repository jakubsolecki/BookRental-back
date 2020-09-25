package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.service.BookService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController{

    private final BookService bookService;

    @GetMapping
    public Page<Book> getAllBooks (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "title") String sortBy) {

        return bookService.getAllBooks(page, size, sort, sortBy);
    }

    @GetMapping(value = "/{id}")
    public Book getBookById(@PathVariable Long id) {

        Book book = bookService.getBookById(id);
        Link selfLink = linkTo(BookController.class).slash(book.getId()).withSelfRel();

        return book.add(selfLink);
    }

    @PostMapping(value = "/")
    public Book addBook(
            @RequestParam String title,
            @RequestParam Long authorId,
            @RequestParam String genre) {

        Book book = bookService.addBook(title, authorId, genre);
        Link selfLink = linkTo(BookController.class).slash(book.getId()).withSelfRel();

        return book.add(selfLink);
    }
}
