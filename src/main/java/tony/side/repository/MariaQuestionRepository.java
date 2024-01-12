package tony.side.repository;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tony.side.domain.Post;
import tony.side.domain.Question;

@Repository
@Slf4j
public class MariaQuestionRepository implements QuestionRepository{

    private final JdbcTemplate template;

    public MariaQuestionRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Question save(Question question) {
        String sql = "insert into portfolio.question(user_id, title, text) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, question.getUserId());
            ps.setString(2, question.getTitle());
            ps.setString(3, question.getText());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        question.setId(key);
        return question;
    }

    @Override
    public List<Post> findUnansweredPostByMemberId(Long memberId) {
        String sql = "SELECT q.id, q.user_id, q.title, q.text "
                + "FROM portfolio.question q "
                + "LEFT JOIN portfolio.answer a ON q.id = a.question_id "
                + "WHERE a.id IS NULL and q.user_id = ?";
        return template.query(sql, noAnsPostRowMapper(), memberId);
    }

    @Override
    public List<Post> findAnsweredPostByMemberId(Long memberId) {
        String sql = "select question.id, question.user_id, question.title, question.text, a.text "
                + "from portfolio.question join portfolio.answer "
                + "a on question.id = a.question_id "
                + "where user_id = ?";
        return template.query(sql, yesAnsPostRowMapper(), memberId);
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from portfolio.question where id = ?";
        template.update(sql, id);
    }

    private RowMapper<Post> noAnsPostRowMapper() {
        return ((rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setUserId(rs.getLong("user_id"));
            post.setTitle(rs.getString("title"));
            post.setQuestion(rs.getString("text"));
            return post;
        });
    }

    private RowMapper<Post> yesAnsPostRowMapper() {
        return ((rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setUserId(rs.getLong("user_id"));
            post.setTitle(rs.getString("title"));
            post.setQuestion(rs.getString("text"));
            post.setAnswer(rs.getString("a.text"));
            return post;
        });
    }
}
