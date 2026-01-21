package minhdoswe.socialnetwork.z.modules.user.internal.auth;

public class RefreshTokenRevokedException extends RuntimeException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}
