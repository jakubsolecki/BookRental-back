package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Address {
    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String zip;
}
