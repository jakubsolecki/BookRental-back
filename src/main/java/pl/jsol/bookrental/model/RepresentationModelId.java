package pl.jsol.bookrental.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public abstract class RepresentationModelId<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    Long id;
}
