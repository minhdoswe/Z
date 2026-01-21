package minhdoswe.socialnetwork.z.modules.user.internal.auth;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
