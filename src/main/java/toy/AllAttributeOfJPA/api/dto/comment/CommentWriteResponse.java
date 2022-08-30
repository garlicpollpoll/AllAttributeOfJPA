package toy.AllAttributeOfJPA.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentWriteResponse {

    private String name;
    private String itemName;
    private String comment;
}
