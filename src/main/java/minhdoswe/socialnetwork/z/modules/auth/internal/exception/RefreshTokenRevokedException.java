package minhdoswe.socialnetwork.z.modules.auth.internal.exception;

public class RefreshTokenRevokedException extends RuntimeException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}
