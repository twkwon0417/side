package tony.side.domain.post;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryPostRepository implements PostRepository {

    private static final ConcurrentHashMap<Long, Post> storage = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong();

    @Override

    public Post save(Post post) {
        post.setId(sequence.addAndGet(1L));
        storage.put(post.getId(), post);

        return post;
    }

    @Override
    public List<Post> findPostsByMemberId(Long memberId) {
        return storage.values().stream()
                .filter(post -> post.getUserId().equals(memberId))
                .toList();
    }

    @Override
    public Post findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
        sequence.set(0L);
    }
}
