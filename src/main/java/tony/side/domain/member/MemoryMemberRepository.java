package tony.side.domain.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static final AtomicLong sequence = new AtomicLong();
    private static final ConcurrentHashMap<Long, Member> storage = new ConcurrentHashMap<>();

    @Override
    public Member save(Member member) {
        member.setId(sequence.addAndGet(1L));
        storage.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findByLoginIdAndPhoneNumber(String loginId, String phoneNumber) {
        return storage.values().stream()
                .filter(member -> member.getLoginId().equals(loginId) && member.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    @Override
    public Member findById(Long id) {     // 없는 id가 조회되는 예외 처리 해야하나?
        return storage.get(id);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());   // 이 리턴값에서는 업데이트 되지 않으므로 동시성 생각 안해도 됨
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
