package pl.jsol.bookrental.service;

import javassist.tools.reflect.CannotCreateException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.ILoanRepository;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;
import pl.jsol.bookrental.model.entity.Book;
import pl.jsol.bookrental.model.entity.LibraryMember;
import pl.jsol.bookrental.model.entity.Loan;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final ILoanRepository loanRepository;
    private final LibraryMemberService libraryMemberService;
    private final BookService bookService;

    @Transactional(rollbackFor = Exception.class)
    public Loan addLoan(@NonNull Long libraryMemberId, @NonNull Long bookId) throws CannotCreateException {
        Loan loan;
        LibraryMember libraryMember = libraryMemberService.getMemberById(libraryMemberId);
        Book book = bookService.getBookById(bookId);

        try {
            loan = new Loan(libraryMember, book);
        } catch (NoCopiesAvailableException error) {
            throw new CannotCreateException(error.getMessage());
        }

        return loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public Optional<Loan> getLoanById(@NonNull Long id) {
        return loanRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Loan> getAllLoans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return loanRepository.findAll(pageable);
    }
}
