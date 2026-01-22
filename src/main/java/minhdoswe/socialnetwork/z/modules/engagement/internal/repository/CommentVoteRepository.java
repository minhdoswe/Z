//package minhdoswe.socialnetwork.z.modules.engagement.internal.repository;
//
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity.CommentVote;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
//
//    Optional<CommentVote> findByComment_IdAndUser_Id(Long commentId, Long userId);
//
//    void deleteByComment_IdAndUser_Id(Long commentId, Long userId);
//}
