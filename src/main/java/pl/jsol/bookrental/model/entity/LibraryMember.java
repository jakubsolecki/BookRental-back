package pl.jsol.bookrental.model.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.jsol.bookrental.model.RepresentationModelId;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class LibraryMember extends RepresentationModelId<LibraryMember> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String phone;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    @Embedded
    private Address address;

    @ToString.Exclude
    private String comments;

    @Builder
    public LibraryMember(String firstName,
                         String lastName,
                         String phone,
                         String email,
                         String city,
                         String street,
                         String zip) {

        if (StringUtils.isAnyEmpty(firstName, lastName, phone, email, city, street, zip)) { // FIXME conflicts with find-by-example strategy
           throw new IllegalArgumentException("Parameters cannot be null nor empty!");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = Address.builder().city(city).street(street).zip(zip).build();
    }

    public void addComment(@NonNull String comment) {
        if (comments == null) {
            comments = comment;
        }
        else {
            comments += "\n" + comment;
        }
    }
}
