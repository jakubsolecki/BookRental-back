package pl.jsol.bookrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import pl.jsol.bookrental.model.BookGenre;

@SpringBootApplication
public class BookrentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookrentalApplication.class, args);
    }

}
