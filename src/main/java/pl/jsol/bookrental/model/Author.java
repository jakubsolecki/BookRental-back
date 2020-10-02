package pl.jsol.bookrental.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Author extends DataSchema<Author> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
//    private final Set<Book> books = new HashSet<>();
//
//    public boolean addBook(Book newBook) {
//        return books.add(newBook);
//    }
}
