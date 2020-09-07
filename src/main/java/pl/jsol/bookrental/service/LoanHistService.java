package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.LoanHistRepository;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.LoanHist;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanHistService {

    private LoanHistRepository loanHistRepository;

    public LoanHist addLoanHist(Loan loan) {
        return loanHistRepository.save(new LoanHist(loan));
    }

    public Optional<LoanHist> getLoanHistById(Long id) {
        return loanHistRepository.findById(id);
    }

    public Page<LoanHist> getAllLoanHists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return loanHistRepository.findAll(pageable);
    }
}
