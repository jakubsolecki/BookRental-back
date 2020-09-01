package pl.jsol.bookrental.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Book;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MockData {
    private final BookRepository bookRepository;

    @PostConstruct
    public void dataInit() {
        for (int i = 0; i < 10; i++) {
            bookRepository.save(new Book(
                    "Title " + i,
                    "Author " + i,
                    "Other"
            ));
        }
    }
}
