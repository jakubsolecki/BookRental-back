package pl.jsol.bookrental;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Genre;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.LoanHist;
import pl.jsol.bookrental.service.BookService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor
class BookrentalApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {

//        Page<Book> pageOfBooks = bookService.findBooks("", "", "f", 10, 0, "asc", "title");
//        assertTrue(pageOfBooks.hasContent());
//        pageOfBooks.getContent().forEach(System.out::println);
    }


}
