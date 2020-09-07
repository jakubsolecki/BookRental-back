package pl.jsol.bookrental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.jsol.bookrental.dal.repository.CopyRepository;
import pl.jsol.bookrental.model.Book;
import pl.jsol.bookrental.model.Copy;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CopyService {

    private final CopyRepository copyRepository;

    public Copy addCopy(Book book) {
        return copyRepository.save(new Copy(book));
    }

    public Optional<Copy> getCopyById(Long id) {
        return copyRepository.findById(id);
    }

//    public Page<Copy> getCopiesOfBook(Book book, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
//        return copyRepository.findCopiesOfBook(book, pageable);
//    }
}
