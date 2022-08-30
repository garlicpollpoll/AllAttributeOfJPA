package toy.AllAttributeOfJPA.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.Comment;

@Data
@AllArgsConstructor
public class CommentViewResponse {

    private String name;
    private String comment;

    public CommentViewResponse(Comment comments) {
        name = comments.getMember().getName();
        comment = comments.getComment();
    }
}
