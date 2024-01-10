package tony.side.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tony.side.SessionConst;
import tony.side.service.LoginService;
import tony.side.domain.Member;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage(LoginDto loginDto) {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Validated LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        log.info("redirectURL= {}", redirectURL);   // 이거 왜 안됨???

        if (bindingResult.hasErrors()) {
            return "login/login";
        }
        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail");
            log.info("로그인 실패");
            return "login/login";
        }
        log.info("{} 사용자 로그인 성공", loginMember.getId());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());

        log.info("어디로 가니?: {}", redirectURL);
        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
