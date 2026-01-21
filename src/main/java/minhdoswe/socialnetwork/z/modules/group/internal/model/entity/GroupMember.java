package minhdoswe.socialnetwork.z.modules.group.internal.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "user_id")
    private Long userId;

}
