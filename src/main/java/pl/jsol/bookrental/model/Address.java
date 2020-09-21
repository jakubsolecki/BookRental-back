package pl.jsol.bookrental.model;

import lombok.*;

import javax.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Address {
    private String city;

    private String street;

    private String zip;
}
