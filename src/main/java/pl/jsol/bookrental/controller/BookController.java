package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.entity.Author;
import pl.jsol.bookrental.model.entity.Book;
import pl.jsol.bookrental.model.entity.BookCopy;
import pl.jsol.bookrental.model.RepresentationModelId;
import pl.jsol.bookrental.service.BookService;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final PagedResourcesAssembler<Book> bookPRAssembler;
    private final PagedResourcesAssembler<BookCopy> bookCopyPRAssembler;

    @GetMapping
    public PagedModel<EntityModel<Book>> getAllBooks (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "title") String sortBy
    ) {
        Page<Book> books = bookService.getAllBooks(page, size, sort, sortBy);

        for (var b : books) {
            var a = b.getAuthor();
            b.add(linkTo(BookController.class).slash(b.getId()).withSelfRel());
            if (!a.hasLinks()) {
                a.add(linkTo(AuthorController.class).slash(a.getId()).withSelfRel());
            }
        }

        return bookPRAssembler.toModel(books);
    }

    @GetMapping("/{id}")
    public Book getBookById (@PathVariable Long id) {

        Book book = bookService.getBookById(id);
        Author author = book.getAuthor();
        author.add(linkTo(AuthorController.class).slash(author.getId()).withSelfRel());

        return book.add(linkTo(BookController.class).slash(book.getId()).withSelfRel());
    }

    @GetMapping("/{id}/copies")
    public CollectionModel<BookCopy> getCopiesOfBook (@PathVariable Long id) {
        try {
            List<BookCopy> bookCopies =  bookService.getBookCopies(id);

            for (var bc : bookCopies) {
                bc.add(linkTo(BookController.class).slash(id).slash("copies").withSelfRel());
            }

            return CollectionModel.of(bookCopies);
        } catch (NoCopiesAvailableException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book postBook (@RequestBody Book book) {

        try {
            Book addedBook = bookService.addBook(book);
            Link selfLink = linkTo(BookController.class).slash(addedBook.getId()).withSelfRel();
            return addedBook.add(selfLink);
        } catch (ResourceAlreadyExistsException ex) {
            RepresentationModelId<?> existingBook = ex.getExistingEntity();
            URI selfLink = linkTo(BookController.class).slash(existingBook.getId()).toUri();

            throw new ResponseStatusException(HttpStatus.SEE_OTHER, selfLink.toString(), ex);
        }
    }

    @PostMapping("/{id}/copies/")
    @ResponseStatus(HttpStatus.CREATED)
    public BookCopy postBookCopy (
            @PathVariable Long id
    ) {
        BookCopy bookCopy = bookService.addBookCopy(id);
        Link selfLink = linkTo(BookController.class)
                .slash(id)
                .slash("copies")
                .slash(bookCopy.getId())
                .withSelfRel();

        return bookCopy.add(selfLink);
    }

    @GetMapping("/{bookId}/copies/{copyId}")
    public BookCopy getBookCopyById (
            @PathVariable Long bookId,
            @PathVariable Long copyId
    ) {
        BookCopy bookCopy = bookService.getBookCopyById(copyId);
        return bookCopy.add(linkTo(BookController.class).slash(bookId).slash("copies").slash(copyId).withSelfRel());
    }

}
