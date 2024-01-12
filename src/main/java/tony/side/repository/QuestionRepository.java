package tony.side.repository;

import java.util.List;
import tony.side.domain.Post;
import tony.side.domain.Question;

public interface QuestionRepository {

    public Question save(Question question);

    public List<Post> findUnansweredPostByMemberId(Long memberId);

    public List<Post> findAnsweredPostByMemberId(Long memberId);

    public Post findById(Long id);

    public void delete(Long id);
}
