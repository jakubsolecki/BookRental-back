package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NonNull
    private int copy_no;

    @ManyToOne
    @NonNull
    private Book book;

    public Copy(Book book) {
        this.book = book;
        this.copy_no = book.getCopies().size() + 1;
    }
}
