package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @NonNull
    private String phone;

    @NonNull
    private String email;

    @Embedded
    @NonNull
    private Address address;

    private String comments;

    public void addComment(String comment) {
        if (comments == null) {
            comments = comment;
        }
        else {
            comments += "\n" + comment;
        }
    }
}