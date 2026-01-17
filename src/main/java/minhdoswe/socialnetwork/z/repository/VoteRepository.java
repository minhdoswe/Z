package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.entity.Vote;
import minhdoswe.socialnetwork.z.enums.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsVoteByPostAndUser(Post post, User user);

    void deleteVoteByPostAndUser(Post post, User user);

    Optional<Vote> findVoteByPostAndUser(Post post, User user);

    Long countVotesByPostAndVoteStatus(Post post, VoteStatus voteStatus);

    List<Vote> findVotesByPostAndVoteStatus(Post post, VoteStatus voteStatus);

    List<Vote> findVotesByUser(User user);
}
