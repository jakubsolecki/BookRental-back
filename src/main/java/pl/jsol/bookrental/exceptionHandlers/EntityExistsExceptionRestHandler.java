package pl.jsol.bookrental.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;

@ControllerAdvice
public class EntityExistsExceptionRestHandler {

    @ResponseBody
    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    String EntityExistsExceptionHandler(EntityExistsException ex) {
        return ex.getMessage();
    }


}
