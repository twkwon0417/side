package tony.side.service;


import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import tony.side.domain.Member;
import tony.side.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member) {
        String hashed = new DigestUtils(SHA_256).digestAsHex(member.getPassword());
        member.setPassword(hashed);
        memberRepository.save(member);
    }

    public Optional<Member> findByLoginIdAndPhoneNumber(String loginId, String phoneNumber) {
        return memberRepository.findByLoginIdAndPhoneNumber(loginId, phoneNumber);
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }

    public void editMemberInfo(Long id, Member newMember) { // 아이디는 못 바꾸지
        Member member = memberRepository.findById(id);
        memberRepository.update(id, newMember);
    }

    public void changePassword(Long id, String password) {     // parameter 바뀔 가능성 농후
        Member member = memberRepository.findById(id);
        member.setPassword(password);
    }

    public boolean isRegisteredLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
