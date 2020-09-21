package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Integer copy_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Book book;

    @Setter
    private boolean available = true;

    public BookCopy(Book book) {
        this.book = book;
        this.copy_no = book.getCopiesQuantity() + 1;
        book.addCopy(this);
    }
}
