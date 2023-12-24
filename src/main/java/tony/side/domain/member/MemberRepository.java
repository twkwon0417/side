package tony.side.domain.member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);

    public Optional<Member> findByLoginIdAndPhoneNumber(String loginId, String phoneNumber);

    public Member findById(Long id);

    public Optional<Member> findByLoginId(String loginId);

    public List<Member> findAll();

    public void clear();
}
