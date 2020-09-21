package pl.jsol.bookrental.dal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("select c from BookCopy c where c.book = ?1")
    Page<BookCopy> findCopiesOfBook(Book book, Pageable pageable);
}
