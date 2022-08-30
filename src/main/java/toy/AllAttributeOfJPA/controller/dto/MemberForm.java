package toy.AllAttributeOfJPA.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPw;
    @NotEmpty
    private String name;
    @NotNull
    private int age;

    private String city;
    private String street;
    private String zipcode;

    public MemberForm(String loginId, String loginPw, String name, int age, String city, String street, String zipcode) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.age = age;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public MemberForm() {
    }
}
