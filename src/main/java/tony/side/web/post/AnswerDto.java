package tony.side.web.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerDto {

    @NotBlank
    @Size(max = 200)
    private String answerText;
}
