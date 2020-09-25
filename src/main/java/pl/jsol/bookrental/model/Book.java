package pl.jsol.bookrental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.jsol.bookrental.exceptions.NoCopiesAvailableException;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Book extends Model<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;

    private BookGenre bookGenre;

    @ToString.Exclude
    @OneToMany(mappedBy = "book")
    private final Set<BookCopy> copies = new HashSet<>();

    @Builder
    public Book(String title, Author author, String genre) {
        this.title = title;
        this.author = author;
        this.bookGenre = genre == null ? null : BookGenre.fromString(genre);
    }

    public boolean addCopy(BookCopy bookCopy) {
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

        throw new NoCopiesAvailableException("No copies available for loan!");
    }
}
