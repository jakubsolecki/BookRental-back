package pl.jsol.bookrental.controller.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;

@ControllerAdvice
public class NotFoundExceptionRestHandler {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView NotFoundExceptionHandler(EntityNotFoundException ex) throws ResponseStatusException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        return mav;
    }
}
