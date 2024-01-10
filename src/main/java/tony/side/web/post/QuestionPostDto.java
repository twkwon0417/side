package tony.side.web.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tony.side.domain.Post;

@Data
public class QuestionPostDto {

    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String question;

    public Post toPost(Long userId) {
        return new Post(userId, this.title, this.question);
    }
}
