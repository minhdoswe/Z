package minhdoswe.socialnetwork.z.modules.user.exception.auth;

public class RefreshTokenRevokedException extends RuntimeException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}
