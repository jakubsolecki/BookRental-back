package pl.jsol.bookrental.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.IBookCopyRepository;
import pl.jsol.bookrental.dal.repository.IBookRepository;
import pl.jsol.bookrental.model.entity.Book;
import pl.jsol.bookrental.service.AuthorService;
import pl.jsol.bookrental.service.BookService;

import java.util.Optional;

public class BookServiceProtectedMethod extends BookService {

    @Autowired
    public BookServiceProtectedMethod(
            IBookRepository iBookRepository,
            AuthorService authorService,
            IBookCopyRepository iBookCopyRepository
    ) {
        super(iBookRepository, iBookCopyRepository, authorService);
    }

    @Override
    @Transactional(readOnly = true)
    protected Optional<Book> verifyBookExistence(Book book) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths("id");
        Example<Book> bookExample = Example.of(book, exampleMatcher);

        return iBookRepository.findOne(bookExample);
    }
}
