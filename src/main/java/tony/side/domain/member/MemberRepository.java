package tony.side.domain.member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);

    public Optional<Member> findByLoginIdAndBirth(String loginId, LocalDate birthDate);

    public Optional<Member> findById(Long id);

    public List<Member> findAll();

    public void clear();
}
