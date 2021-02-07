package pl.jsol.bookrental.model;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class LibraryMember extends DatabaseId<LibraryMember> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String phone;

    @NonNull
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

        if (StringUtils.isAnyEmpty(firstName, lastName, phone, email, city, street, zip)) {
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
