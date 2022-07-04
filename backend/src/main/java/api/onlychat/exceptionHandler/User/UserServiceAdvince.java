package api.onlychat.exceptionHandler.User;

import api.onlychat.exceptionHandler.MessageExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice(basePackages = "api.onlychat.controllers")
public class UserServiceAdvince {
    @ResponseBody
    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<MessageExceptionHandler> userBadRequest(UserBadRequestException exception){
        MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
