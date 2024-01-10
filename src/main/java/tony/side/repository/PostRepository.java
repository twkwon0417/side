package tony.side.repository;

import java.util.List;
import tony.side.domain.Post;

public interface PostRepository {

    public Post save(Post post);

    public List<Post> findUnansweredPostByMemberId(Long memberId);

    public List<Post> findAnsweredPostByMemberId(Long memberId);

    public Post findById(Long id);

    public void delete(Long id);

}
