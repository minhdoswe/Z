package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostsByUserId(Long userId);

    @Query("""
            DELETE from Post b
            WHERE b.deleted = true
            AND b.deletedAt < :cutOff
            """)
    List<Post> deletePermanentlyOlderThan(LocalDateTime cutOff);
}
