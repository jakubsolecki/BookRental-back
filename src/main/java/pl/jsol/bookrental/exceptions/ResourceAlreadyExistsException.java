package pl.jsol.bookrental.exceptions;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.jsol.bookrental.model.DatabaseId;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final DatabaseId<? extends RepresentationModel<?>> existingEntity;

    public ResourceAlreadyExistsException(DatabaseId<? extends RepresentationModel<?>> existingEntity) {
        this.existingEntity = existingEntity;
    }
}

