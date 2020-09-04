package pl.jsol.bookrental;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jsol.bookrental.model.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@SpringBootTest
class BookrentalApplicationTests {

    @Test
    void contextLoads() {
//        System.out.println(Genre.parseGenre("fantasy"));
        LocalDate dueDate = LocalDate.now();
        LocalDate inDate = dueDate.minusDays(1);
        Period period = Period.between(inDate, dueDate);
        System.out.println(period.isNegative());
        System.out.println(new BigDecimal(period.getDays()));
    }


}
