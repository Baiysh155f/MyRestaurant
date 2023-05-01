package myrestaurant.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import myrestaurant.exceptions.ExceptionResponse;
import myrestaurant.exceptions.ExistsElementException;
import myrestaurant.exceptions.NotFoundExceptionId;
import myrestaurant.exceptions.ValidationException;
import myrestaurant.exceptions.NoVacancyException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kurstan
 * @created at 21.03.2023 11:47
 */
@RestControllerAdvice
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @ExceptionHandler(NotFoundExceptionId.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundExceptionId e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(ConstraintViolationException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(ExistsElementException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleExistsException(ExistsElementException e) {
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(NoVacancyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleExistsException(NoVacancyException e) {
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleExistsException(ValidationException e) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof AccessDeniedException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // устанавливаем код ответа 403
            ModelAndView mav = new ModelAndView();
            mav.addObject("message", "У вас нет прав на изменение данных другого пользователя");
            mav.setViewName("error"); // указываем имя представления для ошибки
            return mav;
        }
        return null;
    }
}
