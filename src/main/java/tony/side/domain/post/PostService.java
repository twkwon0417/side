package tony.side.domain.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(Post post) {
        postRepository.save(post);
    }

    public Post getById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getPostByMemberId(Long memberId) {
        return postRepository.findPostsByMemberId(memberId);
    }

    public void edit(Long postId, Post newPost) {
        Post post = postRepository.findById(postId);
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
    }

    public void delete(Long postId) {
        postRepository.delete(postId);
    }
}
