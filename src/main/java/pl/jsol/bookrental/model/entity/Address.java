package pl.jsol.bookrental.model.entity;

import lombok.*;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Address {
    private String city;

    private String street;

    private String zip;
}
