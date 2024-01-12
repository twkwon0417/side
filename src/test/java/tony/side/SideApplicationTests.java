package tony.side;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tony.side.domain.Post;
import tony.side.repository.MariaQuestionRepository;

@SpringBootTest
class SideApplicationTests {

	@Autowired
	MariaQuestionRepository mariaQuestionRepository;

	@Test
	void contextLoads() {
		List<Post> answeredPostByMemberId = mariaQuestionRepository.findAnsweredPostByMemberId(1L);
		for (Post post : answeredPostByMemberId) {
			System.out.println(post);
		}
	}

}
