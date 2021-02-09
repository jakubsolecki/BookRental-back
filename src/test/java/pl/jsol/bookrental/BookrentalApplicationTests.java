package pl.jsol.bookrental;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import pl.jsol.bookrental.model.entity.Book;
import pl.jsol.bookrental.service.BookService;

@SpringBootTest
@RequiredArgsConstructor
class BookrentalApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        Page<Book> pageOfBooks = bookService.getBooksByExample(0, 4, "5",null, null,"asc", "id");
        pageOfBooks.getContent().forEach(System.out::println);
    }


}
