package pl.jsol.bookrental.model.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.jsol.bookrental.model.RepresentationModelId;
import pl.jsol.bookrental.model.UserRole;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class LibraryMember extends RepresentationModelId<LibraryMember> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRole role = UserRole.USER;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @ToString.Exclude
    private String comments;

    @Builder
    public LibraryMember(
            String firstName,
            String lastName,
            String phone,
            String email,
            String city,
            String street,
            String zip
    ) {
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
