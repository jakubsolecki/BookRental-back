package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NonNull
    private Integer copy_no;

    @ManyToOne
    @NonNull
    private Book book;

    public Copy(Book book) {
        this.book = book;
        this.copy_no = book.getCopies().size() + 1;
    }
}
