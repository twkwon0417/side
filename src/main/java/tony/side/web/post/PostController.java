package tony.side.web.post;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import tony.side.SessionConst;
import tony.side.domain.Question;
import tony.side.service.MemberService;
import tony.side.domain.Post;
import tony.side.service.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/{userId}")
    public String outsidePage(@PathVariable Long userId, Model model,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long loginUserId) {
        log.info("사용자 {}가 {}번 사용자의 질문 목록 방문", loginUserId, userId);
        if (Objects.equals(loginUserId, userId)) {
            return "redirect:/post/myPage";
        }
        model.addAttribute("posts", postService.getAnsweredPostByMemberId(userId));
        if(loginUserId != null) {
            model.addAttribute("loggedInMember", memberService.findById(loginUserId).getName());
        }
        return "questions/viewpage/showingpage";
    }

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                         Model model) {
        List<Post> answeredPostByMemberId = postService.getAnsweredPostByMemberId(memberId);

        model.addAttribute("posts", answeredPostByMemberId);
        if(memberId != null) {
            model.addAttribute("loggedInMember", memberService.findById(memberId).getName());
        }

        return "questions/mypage/answered";
    }

    @GetMapping("/myPage/unanswered")
    public String myPageUnanswered(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                   Model model) {
        List<Post> unansweredPostByMemberId = postService.getUnansweredPostByMemberId(memberId);

        model.addAttribute("posts", unansweredPostByMemberId);
        if (memberId != null) {
            model.addAttribute("loggedInMember", memberService.findById(memberId).getName());
        }

        return "questions/mypage/unanswered";
    }

    @GetMapping("/{userId}/ask")
    public String askForm(QuestionPostDto questionPostDto) {
        return "questions/questionask";
    }

    @PostMapping("/{userId}/ask")
    public String addPost(@Validated QuestionPostDto questionPostDto, BindingResult bindingResult, @PathVariable Long userId) {
        if (bindingResult.hasErrors()) {
            return "questions/questionask"; // might error
        }
        Question question = questionPostDto.toQuestion(userId);
        postService.write(question);
        return "redirect:/post/{userId}";
    }

    @PostMapping("/answer/{postId}")
    public String addAnswer(@PathVariable Long postId, String answerText) {
        if (answerText.isBlank() || answerText.trim().length() > 200) {

            return "redirect:/post/myPage/unanswered"; // error?
        }
        log.info(answerText);
        postService.answer(postId, answerText);
        return "redirect:/post/myPage";
    }

    @GetMapping("/delete/{postId}")
    public String deleteAnsweredPost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/myPage";
    }
}
