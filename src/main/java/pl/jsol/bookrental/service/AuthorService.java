package pl.jsol.bookrental.service;

import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.AuthorRepository;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.exceptions.ResourceAlreadyExistsException;
import pl.jsol.bookrental.model.Author;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(rollbackFor = Exception.class)
    public Author addAuthor(@NonNull String firstName, @NonNull String lastName) {

        if(StringUtils.isAnyEmpty(firstName, lastName)) {
            throw new IllegalArgumentException("Argument cannot be empty.");
        }

        Author newAuthor = new Author(firstName, lastName);
        Optional<Author> foundAuthor = verifyAuthorExistence(newAuthor);

        if(foundAuthor.isEmpty()) {
            return authorRepository.save(newAuthor);
        }
        else {
            throw new ResourceAlreadyExistsException((foundAuthor.get()));
        }
    }

    @Transactional(readOnly = true)
    public Page<Author> getAllAuthors(int page, int size, String sort, String sortBy) {

        Sort.Direction sortDirection = (sort != null && sort.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return authorRepository.findAll(pageable);
    }

    @Transactional
    public Author findAuthorById(@NonNull Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author", id));
    }

    @Transactional(readOnly = true)
    protected Optional<Author> verifyAuthorExistence(@NonNull Author author) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Author> authorExample = Example.of(author, exampleMatcher);

        return authorRepository.findOne(authorExample);
    }
}
