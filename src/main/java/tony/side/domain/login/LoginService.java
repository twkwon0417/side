package tony.side.domain.login;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import tony.side.domain.member.Member;
import tony.side.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        String hashed = new DigestUtils(SHA_256).digestAsHex(password);
        return memberRepository.findByLoginId(loginId)
                .filter(x -> x.getPassword().equals(hashed))
                .orElse(null);
    }
}
