package api.onlychat.exceptionHandler.Contact;

public class ContactNotFoundException extends RuntimeException{
    private String message;

    public ContactNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
