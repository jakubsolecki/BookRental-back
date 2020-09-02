package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String author;

    @NonNull
    private Genre genre;

    @OneToMany(fetch = FetchType.LAZY)
    private final Set<Copy> copies = new HashSet<>();

    public boolean addCopy(Copy copy) {
        return copies.add(copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                genre.equals(book.genre) &&
                copies.equals(book.copies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, copies);
    }
}
