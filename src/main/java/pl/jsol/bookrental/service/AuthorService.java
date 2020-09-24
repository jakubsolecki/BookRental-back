package pl.jsol.bookrental.service;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.AuthorRepository;
import pl.jsol.bookrental.model.Author;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

//    @Transactional(rollbackFor = Exception.class)
//    public Author addAuthor(@NotNull String firstName, @NotNull String lastName) {
//
//        if(StringUtils.isAnyEmpty(firstName, lastName)) {
//            throw new IllegalArgumentException("Argument cannot be null or empty!");
//        }
//
//        Author newAuthor = new Author(firstName, lastName);
//
//
//    }

    @Transactional(readOnly = true)
    public Page<Author> getAllAuthors(int page, int size, String sort, String sortBy) {

        Sort.Direction sortDirection = (sort != null && sort.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);

        return authorRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Author> findAuthorById(@NotNull  Long id) {
        return authorRepository.findById(id);
    }
}