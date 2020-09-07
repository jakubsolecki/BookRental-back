package pl.jsol.bookrental.dal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
    @Query("select c from Copy c where c.book = ?1")
    Page<Copy> findCopiesOfBook(Book book, Pageable pageable);
}
