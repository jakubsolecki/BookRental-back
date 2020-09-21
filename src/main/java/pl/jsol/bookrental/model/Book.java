package pl.jsol.bookrental.model;

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

    @NonNull
    private String title;

    @NonNull
    @ManyToOne
    private Author author;

    @NonNull
    private Genre genre;

    @ToString.Exclude
    @OneToMany(mappedBy = "book")
    private final Set<BookCopy> copies = new HashSet<>();

    public Book(String title, Author author, String genre) {
        this.title = title;
        this.author = author;
        author.addBook(this);
        this.genre = Genre.fromString(genre);
    }

    public boolean addCopy(BookCopy bookCopy) {
        return copies.add(bookCopy);
    }

    public int getCopiesQuantity() {
        return copies.size();
    }

    public BookCopy getNextAvailableCopy() throws NoSuchElementException {
        if(copies.isEmpty()) {
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
