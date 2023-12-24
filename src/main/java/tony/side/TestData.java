package tony.side;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tony.side.domain.member.Member;
import tony.side.domain.member.MemberRepository;
import tony.side.domain.post.Post;
import tony.side.domain.post.PostRepository;
import tony.side.domain.post.PostService;

@Component
@RequiredArgsConstructor
public class TestData {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void initTestValue() {
        Member member = new Member("qq", "qq", "qq", "qq", "qq");
        memberRepository.save(member);

        Post post = new Post(1L, "testTile", "testContent");
        post.setAnswer("Test Answer");
        postRepository.save(post);

        Post unansweredPost = new Post(1L, "testTile", "testContent");
        postRepository.save(unansweredPost);
    }

}
