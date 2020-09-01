package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

//@Entity
@Embeddable
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Address {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    String zip;
}
