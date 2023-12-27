package tony.side.web.member;

import lombok.Data;
import tony.side.domain.member.Member;

@Data
public class ChangeInfoDto {
    private String password;
    private String name;
    private String eMail;
    private String phoneNumber;

    public Member toMember(String loginId) {
        return new Member(loginId, this.password, this.name, this.eMail, this.phoneNumber);
    }
}
