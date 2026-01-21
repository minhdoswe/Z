package minhdoswe.socialnetwork.z.modules.content.repository;

import minhdoswe.socialnetwork.z.modules.content.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.content.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            DELETE from Post b
            WHERE b.deleted = true
            AND b.deletedAt < :cutOff
""")
    List<Post> deletePermanentlyOlderThan(LocalDateTime cutOff);

    List<Post> findByUser_IdAndVisibility(Long userId, Visibility visibility);

    List<Post> findByUser_Id(Long userId);
}
