package tony.side.web.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import tony.side.domain.member.Member;

@Getter
@AllArgsConstructor
public class SignUpDto {

    @NotBlank
    @Size(min = 7, max = 12)
    private String loginId;

    @NotBlank
    @Size(min = 7, max = 12)
    private String password;

    @NotBlank
    @Size(min = 2, max = 7)
    private String name;

    @NotBlank
    @Email
    private String eMail;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{11})")
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
