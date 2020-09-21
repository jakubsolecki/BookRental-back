package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.LoanHistRepository;
import pl.jsol.bookrental.model.Loan;
import pl.jsol.bookrental.model.LoanHist;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanHistService {

    private final LoanHistRepository loanHistRepository;

    @Transactional(rollbackFor = Exception.class)
    public LoanHist addLoanHist(Loan loan) {
        return loanHistRepository.save(new LoanHist(loan));
    }

    @Transactional(readOnly = true)
    public Optional<LoanHist> getLoanHistById(Long id) {
        return loanHistRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<LoanHist> getAllLoanHists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return loanHistRepository.findAll(pageable);
    }
}
