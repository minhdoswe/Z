//package minhdoswe.socialnetwork.z.modules.content.internal.model.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import minhdoswe.socialnetwork.z.modules.content.internal.enums.Visibility;
//import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.SQLRestriction;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "posts")
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
//@SQLDelete(sql = "UPDATE posts SET deleted = true, deleted_at = NOW() WHERE id = ?")
//@SQLRestriction("deleted = false")
//public class Post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(name = "user_id")
//    private Long userId;
//
//    private String title;
//
//    @Column(nullable = false)
//    private String content;
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    @Column(name = "modified_at", insertable = false)
//    private LocalDateTime modifiedAt;
//
//    @Column(nullable = false, insertable = false)
//    @Builder.Default
//    private boolean deleted = false;
//
//    @Column(name = "deleted_at", insertable = false)
//    private LocalDateTime deletedAt;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Visibility visibility;
//
//    @Column(name = "upvote_count")
//    @Builder.Default
//    private Long upvoteCount = 0L;
//
//    @Column(name = "downvote_count")
//    @Builder.Default
//    private Long downvoteCount = 0L;
//
//    @Column(name = "vote_score")
//    @Builder.Default
//    private Long voteScore = 0L;
//
//    @Builder.Default
//    private boolean hidden = false;
//}
