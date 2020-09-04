package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public int getCopiesQuantity() {
        return copies.size();
    }

    public Copy getNextAvailableCopy() throws NoSuchElementException {
        Iterator<Copy> it = copies.iterator();
        while(it.hasNext()) {
            Copy copy = it.next();
            if(copy.isAvailable()) {
                return copy;
            }
            it.next();
        }
        throw new NoSuchElementException("No copies available!");
    }
}
