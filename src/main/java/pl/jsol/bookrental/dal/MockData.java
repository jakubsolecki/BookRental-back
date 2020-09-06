package pl.jsol.bookrental.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Genre;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MockData {
    private final BookRepository bookRepository;

    @PostConstruct
    public void dataInit() {
        for (int i = 0; i < 10; i++) {
//            bookRepository.save(Book.builder2()
//                    .title("Title " + i)
//                    .author("Author " + i)
//                    .genre(Genre.parseGenre("Other"))
//                    .build()
//            );
            bookRepository.save(new Book("Title " + i, "Author " + i, Genre.parseGenre("genre")));
        }
    }
}
