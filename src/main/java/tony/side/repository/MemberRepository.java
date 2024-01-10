package tony.side.repository;

import java.util.Optional;
import tony.side.domain.Member;

public interface MemberRepository {

    public Member save(Member member);

    public Optional<Member> findByLoginIdAndPhoneNumber(String loginId, String phoneNumber);

    public Member findById(Long id);

    public Optional<Member> findByLoginId(String loginId);

    public void update(Long id, Member member);
}
