//package minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.PostVoteType;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
//import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "votes",
//        uniqueConstraints = {
//            @UniqueConstraint(
//                    name = "uk_post_user",
//                    columnNames = {"post_id", "user_id"}
//            )
//})
//@EntityListeners(AuditingEntityListener.class)
//@Builder
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class PostVote {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private Post post;
//
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "vote_status")
//    private PostVoteType postVoteType;
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//}
