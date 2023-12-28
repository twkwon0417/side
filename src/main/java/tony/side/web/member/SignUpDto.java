package tony.side.web.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tony.side.domain.member.Member;

@Getter
@AllArgsConstructor
public class SignUpDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String eMail;
    @NotBlank
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
