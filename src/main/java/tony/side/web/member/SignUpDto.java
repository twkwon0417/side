package tony.side.web.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tony.side.domain.member.Member;

@Getter
@AllArgsConstructor
public class SignUpDto {
    private String loginId;
    private String password;
    private String name;
    private String eMail;
    private String phoneNumber;

    @Override
    public String toString() {
        return "SignUpDto{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Member toMember() {
        return new Member(loginId, password, name, eMail, phoneNumber);
    }
}
