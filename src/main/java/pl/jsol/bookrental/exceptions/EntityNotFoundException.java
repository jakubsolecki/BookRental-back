package pl.jsol.bookrental.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType, Long entityId) {
        super(entityType + " " + entityId + " not found.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
