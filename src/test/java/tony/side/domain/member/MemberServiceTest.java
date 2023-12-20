package tony.side.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

    @BeforeEach
    void init() {
        memberRepository.clear();
        Member member1 = new Member("test1", "test1", "test1", "test1@test.com",
                LocalDate.of(2002, 4, 17),
                "01024159056");
        Member member2 = new Member("test2", "test2", "test2", "test2@test.com",
                LocalDate.of(2002, 4, 16),
                "01024169056");

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void editMemberInfoTest() {
        Member newMember = new Member("test3", "test3", "test3", "test3@test.com",
                LocalDate.of(2003, 4, 17),
                "01024169056");
        memberService.editMemberInfo(1L, newMember);

        Member editedMember = memberService.findById(1L);
        assertThat(editedMember.getName()).isEqualTo(newMember.getName());
        assertThat(editedMember.getEMail()).isEqualTo(newMember.getEMail());
    }

    @Test
    void changePasswordTest() {
        memberService.changePassword(1L, "1234");
        Member editedMember = memberService.findById(1L);

        assertThat(editedMember.getPassword()).isEqualTo("1234");
    }
}