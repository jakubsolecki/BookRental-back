package pl.jsol.bookrental.model.entity;

import lombok.*;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String city;

    private String street;

    private String zip;
}
