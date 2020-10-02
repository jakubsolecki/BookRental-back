package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.BookCopy;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("select c from BookCopy c where c.book.id = ?1")
    List<BookCopy> findCopiesOfBook(Long bookId);
}
