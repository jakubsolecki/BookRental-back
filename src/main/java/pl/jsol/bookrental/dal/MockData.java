package pl.jsol.bookrental.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jsol.bookrental.dal.repository.IAuthorRepository;
import pl.jsol.bookrental.dal.repository.IBookRepository;
import pl.jsol.bookrental.model.entity.Author;
import pl.jsol.bookrental.model.entity.Book;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MockData {
    private final IBookRepository IBookRepository;
    private final IAuthorRepository IAuthorRepository;

    @PostConstruct
    public void dataInit() {
        Author author1 = new Author("Ian", "Kowalski");
        Author author2 = new Author("Gal", "Anon");
        IAuthorRepository.save(author1);
        IAuthorRepository.save(author2);
        for (int i = 0; i < 10; i++) {
            IBookRepository.save(new Book("Title " + i, i % 2 == 0 ? author1 : author2, "genre"));
            if(i % 2 == 0) {
                IAuthorRepository.save(author1);
            }
            else {
                IAuthorRepository.save(author2);
            }
        }
    }
}
