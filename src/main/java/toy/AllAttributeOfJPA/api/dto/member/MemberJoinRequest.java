package toy.AllAttributeOfJPA.api.dto.member;

import lombok.Data;
import toy.AllAttributeOfJPA.entity.Address;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberJoinRequest {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPw;
    @NotEmpty
    private String name;
    @NotNull
    private int age;

    private Address address;

    public MemberJoinRequest(String loginId, String loginPw, String name, int age, Address address) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public MemberJoinRequest() {
    }
}
