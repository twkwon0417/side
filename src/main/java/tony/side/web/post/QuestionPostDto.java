package tony.side.web.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tony.side.domain.Question;

@Data
public class QuestionPostDto {

    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String text;

    public Question toQuestion(Long userId) {
        return new Question(userId, this.title, this.text);
    }
}
