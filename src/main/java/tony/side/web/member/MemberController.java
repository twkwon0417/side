package tony.side.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tony.side.domain.member.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(SignUpDto signUpDto) {
        return "/login/signup";
    }

    @PostMapping("/signup")
    public String signupFinish(SignUpDto signUpDto, BindingResult bindingResult) {
        // TODO: id 중복 처리 잡아내기

        memberService.join(signUpDto.toMember());

        log.info("completed {}", signUpDto);

        return "redirect:/login";
    }
}
