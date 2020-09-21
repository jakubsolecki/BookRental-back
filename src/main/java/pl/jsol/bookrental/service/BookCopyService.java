package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.BookCopyRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public BookCopy addCopy(Book book) {
        return bookCopyRepository.save(new BookCopy(book));
    }

    public Optional<BookCopy> getCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }
}
