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

//    public Post getById(Long postId) {
//        return postRepository.findById(postId);
//    }

    public void answer(Long memberId, int postIndex, String text) {
        Long questionId = questionRepository.findUnansweredPostByMemberId(memberId).get(postIndex).getId();
        answerRepository.save(new Answer(questionId, text));
    }

    public List<Post> getUnansweredPostByMemberId(Long memberId) {
        return questionRepository.findUnansweredPostByMemberId(memberId);
    }

    public List<Post> getAnsweredPostByMemberId(Long memberId) {
        return questionRepository.findAnsweredPostByMemberId(memberId);
    }

    public void deleteQuestionAnswered(Long memberId, int postIndex) {
        Long questionId = questionRepository.findAnsweredPostByMemberId(memberId).get(postIndex).getId();
        questionRepository.delete(questionId);
    }

    public void deleteQuestionUnanswered(Long memberId, int postIndex) {
        Long questionId = questionRepository.findUnansweredPostByMemberId(memberId).get(postIndex).getId();
        questionRepository.delete(questionId);
    }
}
