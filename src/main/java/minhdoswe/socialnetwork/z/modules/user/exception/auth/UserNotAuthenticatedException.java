package minhdoswe.socialnetwork.z.modules.user.exception.auth;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
