package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
