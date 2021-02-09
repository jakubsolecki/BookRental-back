package pl.jsol.bookrental.model.entity;

import lombok.*;
import pl.jsol.bookrental.model.RepresentationModelId;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Author extends RepresentationModelId<Author> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
}
