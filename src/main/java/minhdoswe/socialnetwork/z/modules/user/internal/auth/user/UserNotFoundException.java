package minhdoswe.socialnetwork.z.modules.user.internal.auth.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
