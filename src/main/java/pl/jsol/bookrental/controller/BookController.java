package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.service.BookService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<Book> getAllBooks (
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortStrategy", defaultValue = "asc") String sortStrategy,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy) {

        return bookService.getAllBooks(page, size, sortStrategy, sortBy);
    }

    @GetMapping(value = "/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}
