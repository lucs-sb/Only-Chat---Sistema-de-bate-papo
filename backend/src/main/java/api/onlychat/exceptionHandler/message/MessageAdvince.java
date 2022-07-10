package api.onlychat.exceptionHandler.message;

import api.onlychat.exceptionHandler.MessageExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice(value = "api.onlychat.controllers")
public class MessageAdvince {
    @ResponseBody
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> messageNotFound(MessageNotFoundException exception){
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
