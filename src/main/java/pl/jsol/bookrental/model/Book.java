package pl.jsol.bookrental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Book {
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
//        if (author != null) {
//            author.addBook(this);
//        }
        this.bookGenre = genre == null ? null : BookGenre.fromString(genre);
    }

    public boolean addCopy(BookCopy bookCopy) {
        return copies.add(bookCopy);
    }

    public int getCopiesQuantity() {
        return copies.size();
    }

    @JsonIgnore
    public BookCopy getNextAvailableCopy() throws NoSuchElementException {
        if (copies.isEmpty()) {
            throw new NoSuchElementException("No copies available!");
        }

        for (BookCopy bookCopy : copies) {
            if (bookCopy.isAvailable()) {
                return bookCopy;
            }
        }

        throw new NoSuchElementException("No copies available!");
    }
}
