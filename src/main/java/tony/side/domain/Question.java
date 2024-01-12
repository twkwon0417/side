package tony.side.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question {
    private Long id;
    private Long userId;
    private String title;
    private String text;

    public Question(Long userId, String title, String text) {

        this.userId = userId;
        this.title = title;
        this.text = text;
    }
}
