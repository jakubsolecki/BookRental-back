package pl.jsol.bookrental.exceptions.notFound;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
