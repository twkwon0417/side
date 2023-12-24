package tony.side.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.side.domain.member.Member;
import tony.side.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(x -> x.getPassword().equals(password))
                .orElse(null);
    }
}
