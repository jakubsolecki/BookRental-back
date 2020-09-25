package pl.jsol.bookrental.exceptions;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.jsol.bookrental.model.Model;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final Model<? extends RepresentationModel<?>> entityObject;

    public ResourceAlreadyExistsException(Model<? extends RepresentationModel<?>> entityObject) {
        this.entityObject = entityObject;
    }
}

