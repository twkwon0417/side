package tony.side.domain.member;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MariaMemberRepository implements MemberRepository{
    private final JdbcTemplate template;

    public MariaMemberRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
//        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        member.setId(key.longValue());
//        log.info(member.toString());
//        log.info(param.toString());
//        return member;
        String sql = "insert into portfolio.member(login_id, password, name, e_mail, phone_number)"
                + "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getEMail());
            ps.setString(5, member.getPhoneNumber());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public Optional<Member> findByLoginIdAndPhoneNumber(String loginId, String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public Member findById(Long id) {
        String sql = "select id, login_id, password, name, e_mail, phone_number where id = :id";
        try {
            return template.queryForObject(sql, memberRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
    }

    @Override
    public List<Member> findAll() {
        String sql =
    }

    @Override
    public void clear() {

    }

    private RowMapper<Member> memberRowMapper() {
        return (((rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setLoginId(rs.getString("login_id"));
            member.setPassword(rs.getString("password"));
            member.setName(rs.getString("name"));
            member.setEMail(rs.getString("e_mail"));
            member.setPhoneNumber(rs.getString("phone_number"));
            return member;
        }));
    }
}
