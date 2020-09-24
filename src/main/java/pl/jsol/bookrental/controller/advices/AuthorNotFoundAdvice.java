package pl.jsol.bookrental.controller.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jsol.bookrental.exceptions.AuthorNotFoundException;

@ControllerAdvice
public class AuthorNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String BookNotFoundHandler(AuthorNotFoundException ex) {
        return ex.getMessage();
    }
}
