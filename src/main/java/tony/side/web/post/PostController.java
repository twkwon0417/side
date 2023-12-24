package tony.side.web.post;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import tony.side.SessionConst;
import tony.side.domain.post.Post;
import tony.side.domain.post.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{userId}")
    public String outsidePage(@PathVariable Long userId, Model model,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long loginUserId) {
        log.info("사용자 {}가 {}번 사용자의 질문 목록 방문", loginUserId, userId);
        if (Objects.equals(loginUserId, userId)) {
            return "redirect:/post/myPage";
        }
        model.addAttribute("posts", postService.getAnsweredPostByMemberId(userId));
        return "/questions/defaultquestions";
    }

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                         Model model) {
        List<Post> answeredPostByMemberId = postService.getAnsweredPostByMemberId(memberId);

        model.addAttribute("posts", answeredPostByMemberId);
        model.addAttribute("loggedInMember", memberId);

        return "/questions/defaultquestions";
    }

    @GetMapping("/myPage/unanswered")
    public String myPageUnanswered(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                   Model model) {
        List<Post> unansweredPostByMemberId = postService.getUnansweredPostByMemberId(memberId);

        model.addAttribute("posts", unansweredPostByMemberId);
        model.addAttribute("loggedInMember", memberId);

        return "/questions/myquestions";
    }
}
