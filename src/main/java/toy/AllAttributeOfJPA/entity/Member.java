package toy.AllAttributeOfJPA.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;

    private String loginId;
    private String loginPw;

    private Address address;

    public Member(String name, int age, String loginId, String loginPw, Address address) {
        this.address = address;
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.loginPw = loginPw;
    }
}
