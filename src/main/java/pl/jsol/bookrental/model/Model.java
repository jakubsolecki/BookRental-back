package pl.jsol.bookrental.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public abstract class Model<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    Long id;
}
