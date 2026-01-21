package minhdoswe.socialnetwork.z.modules.user.internal.auth;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
