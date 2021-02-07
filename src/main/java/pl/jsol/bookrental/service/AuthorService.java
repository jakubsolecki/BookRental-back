package pl.jsol.bookrental.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.IAuthorRepository;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final IAuthorRepository IAuthorRepository;

    @Transactional(rollbackFor = { Exception.class })
    public Author addAuthor(String firstName, String lastName) throws IllegalArgumentException, ResourceAlreadyExistsException {

        if(StringUtils.isAnyEmpty(firstName, lastName)) {
            throw new IllegalArgumentException("Parameters cannot be null nor empty.");
        }

        Author newAuthor = new Author(firstName, lastName);
        Optional<Author> foundAuthor = verifyAuthorExistence(newAuthor);

        if(foundAuthor.isEmpty()) {
            return IAuthorRepository.save(newAuthor);
        }
        else {
            throw new ResourceAlreadyExistsException((foundAuthor.get()));
        }
    }

    @Transactional(readOnly = true)
    public Page<Author> getAllAuthors(int page, int size, @NonNull Sort.Direction sortDirection, @NonNull String sortBy) {
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        return IAuthorRepository.findAll(pageable);
    }

    @Transactional
    public Author findAuthorById(@NonNull Long id) throws EntityNotFoundException {
        return IAuthorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author", id));
    }

    @Transactional(readOnly = true)
    protected Optional<Author> verifyAuthorExistence(@NonNull Author author) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Author> authorExample = Example.of(author, exampleMatcher);

        return IAuthorRepository.findOne(authorExample);
    }
}
