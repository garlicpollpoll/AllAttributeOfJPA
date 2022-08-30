package toy.AllAttributeOfJPA.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toy.AllAttributeOfJPA.controller.dto.MemberForm;
import toy.AllAttributeOfJPA.entity.Address;
import toy.AllAttributeOfJPA.entity.Member;
import toy.AllAttributeOfJPA.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/join")
    public String joinView() {
        return "member/join";
    }

    @PostMapping("/member/join")
    public String join(@Valid MemberForm form) {
        Member member = new Member(form.getName(), form.getAge(), form.getLoginId(), form.getLoginPw(),
                new Address(form.getCity(), form.getStreet(), form.getZipcode()));
        memberRepository.save(member);

        return "redirect:/";
    }

    @GetMapping("/member/all")
    public String memberAll(Model model) {
        List<Member> findMembers = memberRepository.findAll();

        model.addAttribute("members", findMembers);

        return "member/members";
    }

    @GetMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/member/login")
    public String loginMember(@RequestParam("loginId") String loginId, HttpServletRequest request) {
        Member findMember = memberRepository.findByLoginId(loginId);

        if (findMember != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginId", loginId);
            session.setMaxInactiveInterval(60 * 60);

            return "redirect:/";
        }
        else {
            return "redirect:/member/login";
        }
    }
}
