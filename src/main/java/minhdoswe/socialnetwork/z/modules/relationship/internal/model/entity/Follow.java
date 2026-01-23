package minhdoswe.socialnetwork.z.modules.relationship.internal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_follower_target",
                    columnNames = {"follower_id", "target_id"}
            )
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Builder.Default
    private boolean hidden = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
