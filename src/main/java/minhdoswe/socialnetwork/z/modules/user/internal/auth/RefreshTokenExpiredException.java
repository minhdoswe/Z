package minhdoswe.socialnetwork.z.modules.user.internal.auth;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
