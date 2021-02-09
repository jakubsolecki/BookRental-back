package pl.jsol.bookrental.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jsol.bookrental.dal.repository.ILoanHistRepository;
import pl.jsol.bookrental.model.entity.Loan;
import pl.jsol.bookrental.model.entity.LoanHist;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanHistService {

    private final ILoanHistRepository ILoanHistRepository;

    @Transactional(rollbackFor = Exception.class)
    public LoanHist addLoanHist(@NonNull Loan loan) {
        return ILoanHistRepository.save(new LoanHist(loan));
    }

    @Transactional(readOnly = true)
    public Optional<LoanHist> getLoanHistById(@NonNull Long id) {
        return ILoanHistRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<LoanHist> getAllLoanHists(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ILoanHistRepository.findAll(pageable);
    }
}
