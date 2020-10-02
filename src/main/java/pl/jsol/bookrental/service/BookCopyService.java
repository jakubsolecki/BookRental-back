package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.BookCopyRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookService bookService;

    @Transactional(rollbackFor = Exception.class)
    public BookCopy addBookCopy(Long bookId) {

        Book book = bookService.getBookById(bookId);
        return bookCopyRepository.save(new BookCopy(book));
    }

    @Transactional(readOnly = true)
    public Optional<BookCopy> getBookCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }
}
