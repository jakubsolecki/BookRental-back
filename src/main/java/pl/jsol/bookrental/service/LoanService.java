package pl.jsol.bookrental.service;

import javassist.tools.reflect.CannotCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.LoanRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.Member;

import javax.naming.CannotProceedException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private LoanRepository loanRepository;

    public Loan addLoan(Member member, Book book) throws CannotCreateException {
        Loan loan = null;

        try {
            loan = new Loan(member, book);
        } catch (CannotProceedException error) {
            throw new CannotCreateException(error.getMessage());
        }

        return loanRepository.save(loan);
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Page<Loan> getAllLoans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return loanRepository.findAll(pageable);
    }
}
