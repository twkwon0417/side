package tony.side.repository;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tony.side.domain.Post;

@Repository
@Slf4j
public class MariaPostRepository implements PostRepository {

    private final JdbcTemplate template;

    public MariaPostRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        String sql = "insert into portfolio.post(user_id, title, question, answer) values(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getQuestion());
            ps.setString(4, post.getAnswer());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        post.setId(key);
        return post;
    }

    @Override
    public List<Post> findUnansweredPostByMemberId(Long memberId) {
        String sql = "select id, user_id, title, question, answer from portfolio.post "
                + "where user_id = ? and answer is null";
        log.info("{} has been inquired", memberId);
        return template.query(sql, postRowMapper(), memberId);
    }

    @Override
    public List<Post> findAnsweredPostByMemberId(Long memberId) {
        String sql = "select id, user_id, title, question, answer from portfolio.post "
                + "where user_id = ? and answer is not null";

        return template.query(sql, postRowMapper(), memberId);
    }

    @Override
    public Post findById(Long id) {
        String sql = "select id, user_id, title, question, answer from portfolio.post where id = ?";
        try {
            return template.queryForObject(sql, postRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from portfolio.post where id = ?";
        template.update(sql, id);
    }

    private RowMapper<Post> postRowMapper() {
        return ((rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setUserId(rs.getLong("user_id"));
            post.setTitle(rs.getString("title"));
            post.setQuestion(rs.getString("question"));
            post.setAnswer(rs.getString("answer"));
            return post;
        });
    }
}
