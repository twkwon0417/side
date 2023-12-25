package tony.side.web.post;

import lombok.Data;
import tony.side.domain.post.Post;

@Data
public class QuestionPostDto {

    private String title;
    private String question;

    public Post toPost(Long userId) {
        return new Post(userId, this.title, this.question);
    }
}
