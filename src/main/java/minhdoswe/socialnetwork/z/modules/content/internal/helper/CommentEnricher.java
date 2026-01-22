//package minhdoswe.socialnetwork.z.modules.content.internal.helper;
//
//import lombok.RequiredArgsConstructor;
//import minhdoswe.socialnetwork.z.modules.content.internal.mapper.CommentMapper;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
//import minhdoswe.socialnetwork.z.modules.user.UserAPI;
//import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class CommentEnricher {
//
//    private final UserAPI userAPI;
//    private final CommentMapper commentMapper;
//
//    public List<CommentResponse> enrichList(List<Comment> commentList) {
//
//        Set<Long> userIdSet = commentList.stream()
//                .map(Comment::getUserId)
//                .collect(Collectors.toSet());
//
//        Map<Long, UserDTO> userDTOMap = userAPI.getUserDTO(userIdSet);
//
//        return commentList.stream().map(
//                comment -> commentMapper.toCommentResponse(comment)
//                        .setUserDTO(userDTOMap.get(comment.getUserId()))
//                )
//                        .toList();
//    }
//
//    public CommentResponse enrichOne(Comment comment) {
//
//        UserDTO userDTO = userAPI.getUserDTO(comment.getUserId());
//
//        return commentMapper.toCommentResponse(comment).setUserDTO(userDTO);
//    }
//}
