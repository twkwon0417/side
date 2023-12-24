package tony.side.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import tony.side.SessionConst;
import tony.side.domain.post.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{userId}")
    public String outsidePage(@PathVariable Long userId, Model model,
                              @SessionAttribute(required = false) Long loginUserId) {
        log.info("{}", loginUserId);
        model.addAttribute("posts", postService.getPostByMemberId(userId));
        return "/questions/defaultquestions";
    }

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId, Model model) {
        model.addAttribute("loggedInMember", memberId);

        return "/questions/myquestions";
    }
}
