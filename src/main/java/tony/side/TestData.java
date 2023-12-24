package tony.side;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tony.side.domain.post.Post;
import tony.side.domain.post.PostRepository;
import tony.side.domain.post.PostService;

@Component
@RequiredArgsConstructor
public class TestData {

    private final PostRepository postRepository;

    @PostConstruct
    public void initTestValue() {
        Post post = new Post(1L, "testTile", "testContent");
        postRepository.save(post);
    }
}
