package pl.jsol.bookrental.controller.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;

@ControllerAdvice
public class NotFoundExceptionRestHandler {

    @ResponseBody
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String NotFoundExceptionHandler(EntityNotFoundException ex) {
        return ex.getMessage();
    }
}
