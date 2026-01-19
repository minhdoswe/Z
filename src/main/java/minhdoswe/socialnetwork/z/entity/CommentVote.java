package minhdoswe.socialnetwork.z.entity;

import jakarta.persistence.*;
import minhdoswe.socialnetwork.z.enums.VoteStatus;
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
    @Column(name = "vote_status")
    VoteStatus voteStatus;
}
