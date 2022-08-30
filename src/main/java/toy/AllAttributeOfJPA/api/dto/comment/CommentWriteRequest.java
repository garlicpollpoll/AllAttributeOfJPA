package toy.AllAttributeOfJPA.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CommentWriteRequest {

    private String comment;

    public CommentWriteRequest(String comment) {
        this.comment = comment;
    }

    public CommentWriteRequest() {
    }
}
