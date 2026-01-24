package minhdoswe.socialnetwork.z.modules.auth.internal.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
