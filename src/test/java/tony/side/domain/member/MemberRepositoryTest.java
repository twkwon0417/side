package tony.side.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tony.side.domain.member.Member;
import tony.side.domain.member.MemberRepository;
import tony.side.domain.member.MemoryMemberRepository;

public class MemberRepositoryTest {

    MemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    void init() {
        memberRepository.clear();
        Member member1 = new Member("test1", "test1", "test1", "test1@test.com",
                "01024159056");
        Member member2 = new Member("test2", "test2", "test2", "test2@test.com",
                "01024169056");

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void saveTest() {
        Member member = new Member();
        Member savedMember = memberRepository.save(member);
        assertThat(member.getId()).isEqualTo(3);
        assertThat(savedMember).isEqualTo(member);
    }

    @Test
    void findByLoginIdAndPhoneNumberTest() {
        Optional<Member> foundMember = memberRepository.findByLoginIdAndPhoneNumber("test1", "01024159056");
        assertThat(foundMember.get()).isEqualTo(memberRepository.findById(1L));
    }

    @Test
    void findAllTest() {
        List<Member> allMembers = memberRepository.findAll();

        assertThat(allMembers).containsExactly(memberRepository.findById(1L), memberRepository.findById(2L));
    }
}
