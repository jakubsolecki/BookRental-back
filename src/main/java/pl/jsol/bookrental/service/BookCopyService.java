package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.IBookCopyRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.BookCopy;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final IBookCopyRepository IBookCopyRepository;
    private final BookService bookService;

    @Transactional(rollbackFor = Exception.class)
    public BookCopy addBookCopy(Long bookId) {

        Book book = bookService.getBookById(bookId);
        return IBookCopyRepository.save(new BookCopy(book));
    }

    @Transactional(readOnly = true)
    public Optional<BookCopy> getBookCopyById(Long id) {
        return IBookCopyRepository.findById(id);
    }
}
