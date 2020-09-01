package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.CopyRepository;
import pl.jsol.bookrental.model.Book;

@Service
@RequiredArgsConstructor
public class CopyService {
    private final CopyRepository copyRepository;
}
