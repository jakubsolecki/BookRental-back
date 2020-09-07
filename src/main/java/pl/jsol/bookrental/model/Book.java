package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
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

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private final Set<Copy> copies = new HashSet<>();

    public boolean addCopy(Copy copy) {
        return copies.add(copy);
    }

    public int getCopiesQuantity() {
        return copies.size();
    }

    public Copy getNextAvailableCopy() throws NoSuchElementException {
        if(copies.isEmpty()) {
            throw new NoSuchElementException("No copies available!");
        }

        for (Copy copy : copies) {
            if (copy.isAvailable()) {
                return copy;
            }
        }
        throw new NoSuchElementException("No copies available!");
    }
}
