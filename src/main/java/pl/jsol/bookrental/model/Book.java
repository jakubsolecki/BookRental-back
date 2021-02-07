package pl.jsol.bookrental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Data
public class Book extends DatabaseId<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;

    private BookGenre bookGenre;

    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private final List<BookCopy> copies = new ArrayList<>();

    @Builder
    public Book(String title, @NonNull Author author, String genre) {
        if (StringUtils.isAnyEmpty(title, genre)) {
            throw new IllegalArgumentException("Book title nor genre cannot be null or empty!");
        }

        this.title = title;
        this.author = author;
        this.bookGenre = BookGenre.fromString(genre);
    }

    public boolean addCopy(@NonNull BookCopy bookCopy) {
        return copies.add(bookCopy);
    }

    public int getCopiesQuantity() {
        return copies.size();
    }

    @JsonIgnore
    public BookCopy getNextAvailableCopy() throws NoCopiesAvailableException {
        if (copies.isEmpty()) {
            throw new NoCopiesAvailableException("Book with id: " + this.id + " does not have any copies yet!");
        }

        for (BookCopy bookCopy : copies) {
            if (bookCopy.isAvailable()) {
                return bookCopy;
            }
        }

        throw new NoCopiesAvailableException("No copies available!");
    }
}
