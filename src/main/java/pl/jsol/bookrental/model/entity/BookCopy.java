package pl.jsol.bookrental.model.entity;

import lombok.*;
import pl.jsol.bookrental.model.RepresentationModelId;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class BookCopy extends RepresentationModelId<BookCopy> {
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

    public BookCopy(@NonNull Book book) {
        this.book = book;
        this.copy_no = book.getCopiesQuantity() + 1;
        book.addCopy(this);
    }
}
