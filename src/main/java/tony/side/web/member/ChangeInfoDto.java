package tony.side.web.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tony.side.domain.Member;

@Data
public class ChangeInfoDto {

    @NotBlank
    @Size(min = 7, max = 12)
    private String password;

    @NotBlank
    @Size(min = 2, max = 7)
    private String name;

    @Email
    @NotBlank
    @Size(max = 40)
    private String eMail;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phoneNumber;

    public Member toMember(String loginId) {
        return new Member(loginId, this.password, this.name, this.eMail, this.phoneNumber);
    }
}
