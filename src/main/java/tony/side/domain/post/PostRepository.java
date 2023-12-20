package tony.side.domain.post;

import java.util.List;

public interface PostRepository {

    public Post save(Post post);

    public List<Post> findPostsByMemberId(Long memberId);

    public Post findById(Long id);

    public void delete(Long id);

    public void clear();
}
