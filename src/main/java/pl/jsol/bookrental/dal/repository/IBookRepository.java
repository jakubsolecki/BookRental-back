package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.entity.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
}
