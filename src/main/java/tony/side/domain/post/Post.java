package tony.side.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String question;
    private String answer;

    public Post(Long userId, String title, String question) {
        this.userId = userId;
        this.title = title;
        this.question = question;
        this.answer = null;
    }

    public Post() {
    }
}
