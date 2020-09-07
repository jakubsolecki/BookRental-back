package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Integer copy_no;

    @ManyToOne
    @NonNull
    private Book book;

    @Setter
    private boolean available = true;

    public Copy(Book book) {
        this.book = book;
        this.copy_no = book.getCopiesQuantity() + 1;
        book.addCopy(this);
    }
}
