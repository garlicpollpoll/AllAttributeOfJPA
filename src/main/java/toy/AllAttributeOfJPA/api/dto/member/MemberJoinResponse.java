package toy.AllAttributeOfJPA.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.Member;

@Data
@AllArgsConstructor
public class MemberJoinResponse {

    private String name;
    private int age;

    public MemberJoinResponse(Member member) {
        name = member.getName();
        age = member.getAge();
    }
}
