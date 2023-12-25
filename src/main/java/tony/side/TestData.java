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

        Post post22 = new Post(1L, "testTile", "testContent");
        post22.setAnswer("Test Answer");
        postRepository.save(post22);

        Post unansweredPost = new Post(1L, "testTile", "testContent");
        postRepository.save(unansweredPost);


        Member member2 = new Member("qqq", "qqq", "qqq", "qqq", "qqq");
        memberRepository.save(member2);

        Post post2 = new Post(2L, "testTile", "testContent");
        post2.setAnswer("Test Answer");
        postRepository.save(post2);

        Post unansweredPost2 = new Post(2L, "testTile", "testContent");
        postRepository.save(unansweredPost2);
    }

}
