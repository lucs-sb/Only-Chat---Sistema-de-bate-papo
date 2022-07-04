package api.onlychat.exceptionHandler.User;

public class UserBadRequestException extends RuntimeException {
    private String message;

    public UserBadRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
