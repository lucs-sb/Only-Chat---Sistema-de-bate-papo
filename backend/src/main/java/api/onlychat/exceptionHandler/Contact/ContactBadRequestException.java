package api.onlychat.exceptionHandler.Contact;

public class ContactBadRequestException extends RuntimeException{
    private String message;

    public ContactBadRequestException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
