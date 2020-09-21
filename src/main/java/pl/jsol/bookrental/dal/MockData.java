package pl.jsol.bookrental.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jsol.bookrental.dal.repository.AuthorRepository;
import pl.jsol.bookrental.dal.repository.BookRepository;
import pl.jsol.bookrental.model.Author;
import pl.jsol.bookrental.model.Book;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MockData {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @PostConstruct
    public void dataInit() {
        Author author1 = new Author("Ian", "Kowalski");
        Author author2 = new Author("Gal", "Anon");
        authorRepository.save(author1);
        authorRepository.save(author2);
        for (int i = 0; i < 10; i++) {
            bookRepository.save(new Book("Title " + i, i % 2 == 0 ? author1 : author2, "genre"));
            if(i % 2 == 0) {
                authorRepository.save(author1);
            }
            else {
                authorRepository.save(author2);
            }
        }
    }
}
