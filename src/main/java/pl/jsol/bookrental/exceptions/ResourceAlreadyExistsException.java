package pl.jsol.bookrental.exceptions;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.jsol.bookrental.model.DataSchema;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final DataSchema<? extends RepresentationModel<?>> existingEntity;

    public ResourceAlreadyExistsException(DataSchema<? extends RepresentationModel<?>> existingEntity) {
        this.existingEntity = existingEntity;
    }
}

