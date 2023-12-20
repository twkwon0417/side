package tony.side.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String content;
}
