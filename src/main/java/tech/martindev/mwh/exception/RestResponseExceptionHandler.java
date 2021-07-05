package tech.martindev.mwh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseExceptionHandler  {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public String handleAccountAlreadyExistsException() {
        return "O usuário informado já existe em nosso sistema, utilize outro.";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({AccountNotFoundException.class})
    public String handlAccountNotFoundException() {
        return "A conta não foi encontrada";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountPasswordWrongException.class)
    public String handleAAccountPasswordWrongException() {
        return "A senha informada está incorreta.";
    }
}
