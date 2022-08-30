package toy.AllAttributeOfJPA.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.AllAttributeOfJPA.api.dto.member.MemberJoinRequest;
import toy.AllAttributeOfJPA.api.dto.member.MemberJoinResponse;
import toy.AllAttributeOfJPA.api.dto.result.Result;
import toy.AllAttributeOfJPA.entity.Member;
import toy.AllAttributeOfJPA.repository.MemberRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberRepository memberRepository;

    /**
     * MemberJoinRequest
     * MemberJoinResponse
     */
    @PostMapping("/api/member/join")
    public Result apiJoin(@Valid @RequestBody MemberJoinRequest request) {
        Member member = new Member(request.getName(), request.getAge(), request.getLoginId(), request.getLoginPw(), request.getAddress());

        memberRepository.save(member);

        MemberJoinResponse memberJoinResponse = new MemberJoinResponse(request.getName(), request.getAge());

        return new Result(memberJoinResponse);
    }

    /**
     * MemberJoinResponse
     */
    @GetMapping("/api/member/all")
    public Result apiFindAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberJoinResponse> collect = members.stream().map(x -> new MemberJoinResponse(x)).collect(Collectors.toList());

        return new Result(collect);
    }
}
