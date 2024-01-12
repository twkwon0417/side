package tony.side.repository;


import java.sql.PreparedStatement;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tony.side.domain.Answer;

@Repository
public class MariaAnswerRepository implements AnswerRepository {

    private final JdbcTemplate template;

    public MariaAnswerRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Answer save(Answer answer) {
        String sql = "insert into portfolio.answer(question_id, text) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, answer.getQuestionId());
            ps.setString(2, answer.getText());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        answer.setId(key);
        return answer;
    }

}
