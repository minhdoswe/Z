package minhdoswe.socialnetwork.z.modules.auth.internal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_revoked")
    private boolean isRevoked;
}
