package pl.jsol.bookrental;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jsol.bookrental.model.Genre;

@SpringBootTest
class BookrentalApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Genre.parseGenre("fantasy"));
    }

}
