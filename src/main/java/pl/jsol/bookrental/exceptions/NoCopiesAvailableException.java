package pl.jsol.bookrental.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoCopiesAvailableException extends RuntimeException{

    public NoCopiesAvailableException(String message) {
        super(message);
    }
}
