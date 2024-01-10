package tony.side.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import tony.side.SessionConst;
import tony.side.domain.Member;
import tony.side.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(SignUpDto signUpDto) {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String signupFinish(@Validated SignUpDto signUpDto, BindingResult bindingResult) {
        // TODO: id 중복 처리 잡아내기
        if (bindingResult.hasErrors()) {
            return "login/signup";
        }

        if (memberService.isRegisteredLoginId(signUpDto.getLoginId())) {
            bindingResult.reject("isRegisteredId", "default");
            log.info("id 중복");
            return "login/signup";
        }

        memberService.join(signUpDto.toMember());

        log.info("completed {}", signUpDto);

        return "redirect:/login";
    }

    @GetMapping("/findPassword")
    public String addForm(FindPasswordDto findPasswordDto) {
        return "login/password";
    }

    @PostMapping("/findPassword")
    public String findPassword(@Validated FindPasswordDto findPasswordDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/password";
        }
        Member member = memberService.findByLoginIdAndPhoneNumber(
                        findPasswordDto.getLoginId(), findPasswordDto.getPhoneNumber())
                .orElse(null);
        if (member == null) {
            bindingResult.reject("notRegisteredInformation", "오류");
            return "login/password";
        }
        model.addAttribute("password", member.getPassword());
        return "login/password";
    }

    @GetMapping("/changeUserInfo")
    public String changeForm(ChangeInfoDto changeInfoDto,
                             @SessionAttribute(name =SessionConst.LOGIN_MEMBER) Long userId) {
        Member member = memberService.findById(userId);
        changeInfoDto.setName(member.getName());
        changeInfoDto.setPassword(member.getPassword());
        changeInfoDto.setPhoneNumber(member.getPhoneNumber());
        changeInfoDto.setEMail(member.getEMail());
        return "login/changeinfo";
    }

    @PostMapping("/changeUserInfo")
    public String changeUserInfo(@Validated ChangeInfoDto changeInfoDto, BindingResult bindingResult,
                                 @SessionAttribute(name =SessionConst.LOGIN_MEMBER) Long userId) {
        if (bindingResult.hasErrors()) {
            return "login/changeinfo";
        }
        Member changeMember = changeInfoDto.toMember(memberService.findById(userId).getLoginId());
        memberService.editMemberInfo(userId, changeMember);
        return "redirect:/post/myPage";
    }
}
