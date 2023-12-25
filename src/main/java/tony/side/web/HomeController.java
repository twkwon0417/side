package tony.side.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tony.side.SessionConst;
import tony.side.web.login.LoginDto;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long userId, Model model) {
        if (userId ==  null) {
            model.addAttribute(new LoginDto());
            return "/login/login";
        }

        return "redirect:/post/myPage";
    }
}
