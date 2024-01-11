package tony.side.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import tony.side.SessionConst;
import tony.side.domain.Member;
import tony.side.service.AuthService;
import tony.side.service.MemberService;
import tony.side.web.member.ChangeInfoDto;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    @GetMapping("/mail")
    public String MailPage(HttpServletRequest request, @ModelAttribute(value = "email") String email,
                           @RequestParam(value = "id") Long id, KeyDto keyDto){
        log.info("email={}", email);
        log.info("id={}", id);
        Long key = (long) authService.sendMail(email);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.AUTH_KEY, List.of(key, id));
        session.setMaxInactiveInterval(300);    //

        return "login/email";
    }

    @PostMapping("/mail")
    public String MailSend(KeyDto keyDto, BindingResult bindingResult,
                           @SessionAttribute(value = SessionConst.AUTH_KEY) List<Long> list){
        log.info("{}", list.get(0));
        log.info("{}", list.get(1));

        if (Objects.equals(list.get(0), keyDto.getNumber())) {
            log.info("yes");
            return "redirect:/mail/changepassword";
        }

        return "login/email";
    }

    @GetMapping("mail/changepassword")
    public String passwordForm(@SessionAttribute(value = SessionConst.AUTH_KEY) List<Long> list,
                      ChangeInfoDto changeInfoDto, BindingResult bindingResult) {
        Member member = memberService.findById(list.get(1));
        changeInfoDto.setName(member.getName());
        changeInfoDto.setPassword(member.getPassword());
        changeInfoDto.setPhoneNumber(member.getPhoneNumber());
        changeInfoDto.setEMail(member.getEMail());
        return "login/changepassword";
    }

    @PostMapping("mail/changepassword")
    public String changePassword(@Validated ChangeInfoDto changeInfoDto, BindingResult bindingResult,
                           @SessionAttribute(value = SessionConst.AUTH_KEY) List<Long> list) {
        if (bindingResult.hasErrors()) {
            return "login/changepassword";
        }
        Member changeMember = changeInfoDto.toMember(memberService.findById(list.get(1)).getLoginId());
        memberService.editMemberInfo(list.get(1), changeMember);
        return "redirect:/";
    }
}
