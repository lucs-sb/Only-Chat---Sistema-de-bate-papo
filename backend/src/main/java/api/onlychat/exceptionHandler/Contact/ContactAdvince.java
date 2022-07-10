package api.onlychat.exceptionHandler.Contact;

import api.onlychat.exceptionHandler.MessageExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice(basePackages = "api.onlychat.controllers")
public class ContactAdvince {
    @ResponseBody
    @ExceptionHandler(ContactBadRequestException.class)
    public ResponseEntity<MessageExceptionHandler> contactBadRequest(ContactBadRequestException exception){
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> contactNotFound(ContactNotFoundException exception){
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new  ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
