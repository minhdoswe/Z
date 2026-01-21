package minhdoswe.socialnetwork.z.modules.user.internal.auth;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
