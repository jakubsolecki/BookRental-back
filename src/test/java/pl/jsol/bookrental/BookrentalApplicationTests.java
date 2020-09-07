package pl.jsol.bookrental;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jsol.bookrental.service.BookService;

@SpringBootTest
@RequiredArgsConstructor
class BookrentalApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {

    }


}
