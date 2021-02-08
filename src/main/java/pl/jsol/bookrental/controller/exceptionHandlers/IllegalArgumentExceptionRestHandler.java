package pl.jsol.bookrental.controller.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class IllegalArgumentExceptionRestHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        return mav;
    }
}
