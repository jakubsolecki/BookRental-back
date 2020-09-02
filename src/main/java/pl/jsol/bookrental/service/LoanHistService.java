package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.LoanHistRepository;

@Service
@RequiredArgsConstructor
public class LoanHistService {
    private LoanHistRepository loanHistRepository;
}
