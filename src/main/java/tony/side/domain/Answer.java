package tony.side.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {
    private Long id;
    private Long questionId;
    private String text;

    public Answer(Long questionId, String text) {
        this.questionId = questionId;
        this.text = text;
    }
}
