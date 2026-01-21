package minhdoswe.socialnetwork.z.modules.user.exception.auth.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
