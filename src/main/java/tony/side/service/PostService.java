package tony.side.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.side.domain.Answer;
import tony.side.domain.Post;
import tony.side.domain.Question;
import tony.side.repository.AnswerRepository;
import tony.side.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public void write(Question question) {
        questionRepository.save(question);
    }

    public void answer(Long postId, String text) {
        Answer answer = new Answer(postId, text);
        answerRepository.save(answer);
    }

    public List<Post> getUnansweredPostByMemberId(Long memberId) {
        return questionRepository.findUnansweredPostByMemberId(memberId);
    }

    public List<Post> getAnsweredPostByMemberId(Long memberId) {
        return questionRepository.findAnsweredPostByMemberId(memberId);
    }

    public void deletePost(Long postId) {
        questionRepository.delete(postId);
    }
}
