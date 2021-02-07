package pl.jsol.bookrental.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.BookCopy;

import java.util.List;

@Repository
public interface IBookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("SELECT c FROM BookCopy c WHERE c.book.id = ?1")
    List<BookCopy> findCopiesOfBook(Long bookId);
}
