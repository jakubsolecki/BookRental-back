package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    @Embedded
    private Address address;

    private String comments;

    @Builder
    public LibraryMember(String firstName,
                         String lastName,
                         String phone,
                         String email,
                         String city,
                         String street,
                         String zip) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = Address.builder().city(city).street(street).zip(zip).build();
    }

    public void addComment(String comment) {
        if (comments == null) {
            comments = comment;
        }
        else {
            comments += "\n" + comment;
        }
    }
}
