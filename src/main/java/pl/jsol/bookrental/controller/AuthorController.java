package pl.jsol.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.entity.Author;
import pl.jsol.bookrental.model.RepresentationModelId;
import pl.jsol.bookrental.service.AuthorService;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final PagedResourcesAssembler<Author> authorPRAssembler;

    @GetMapping
    public PagedModel<EntityModel<Author>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "lastName") String sortBy
    ) {
        Page<Author> authors = authorService.getAllAuthors(page, size, sort, sortBy);

        for (var a : authors) {
            a.add(linkTo(AuthorController.class).slash(a.getId()).withSelfRel());
        }

        return authorPRAssembler.toModel(authors);
    }

    @GetMapping(value = "/{id}")
    public Author getAuthorById(@PathVariable Long id) {

        Author author = authorService.findAuthorById(id);
        Link selfLink = linkTo(AuthorController.class).slash(author.getId()).withSelfRel();

        return author.add(selfLink);
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Author postAuthor(@RequestBody Author author) {

        try {
            Author postedAuthor = authorService.addAuthor(author);
            Link selfLink = linkTo(AuthorController.class).slash(postedAuthor.getId()).withSelfRel();
            return postedAuthor.add(selfLink);
        } catch (ResourceAlreadyExistsException ex) {
            RepresentationModelId<?> existingAuthor = ex.getExistingEntity();
            URI selfLink = linkTo(AuthorController.class).slash(existingAuthor.getId()).toUri();

            throw new ResponseStatusException(HttpStatus.SEE_OTHER, selfLink.toString(), ex);
        }
    }

}
