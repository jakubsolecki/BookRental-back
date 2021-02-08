package pl.jsol.bookrental.exceptions;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.jsol.bookrental.model.RepresentationModelId;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final RepresentationModelId<? extends RepresentationModel<?>> existingEntity;

    public ResourceAlreadyExistsException(RepresentationModelId<? extends RepresentationModel<?>> existingEntity) {
        this.existingEntity = existingEntity;
    }
}

