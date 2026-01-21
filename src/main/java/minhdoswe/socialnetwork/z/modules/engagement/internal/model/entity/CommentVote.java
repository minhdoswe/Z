package minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.CommentVoteType;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "comment_votes",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_comment_user",
                    columnNames = {"comment_id", "user_id"}
            )
        })
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_vote_status")
    CommentVoteType commentVoteType;
}
