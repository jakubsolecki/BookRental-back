package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.DatabaseId;
import pl.jsol.bookrental.service.AuthorService;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public Page<Author> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "lastName") String sortBy) {

        return authorService.getAllAuthors(page, size, sort, sortBy);
    }

    @GetMapping(value = "/{id}")
    public Author getAuthorById(@PathVariable Long id) {

        Author author = authorService.findAuthorById(id);
        Link selfLink = linkTo(AuthorController.class).slash(author.getId()).withSelfRel();

        return author.add(selfLink);
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Author postAuthor(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        try {
            Author author = authorService.addAuthor(firstName, lastName);
            Link selfLink = linkTo(AuthorController.class).slash(author.getId()).withSelfRel();
            return author.add(selfLink);
        } catch (ResourceAlreadyExistsException ex) {
            DatabaseId<?> existingAuthor = ex.getExistingEntity();
            URI selfLink = linkTo(AuthorController.class).slash(existingAuthor.getId()).toUri();

            throw new ResponseStatusException(HttpStatus.SEE_OTHER, selfLink.toString(), ex);
        }
    }

}
