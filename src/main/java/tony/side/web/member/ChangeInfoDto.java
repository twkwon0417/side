package tony.side.web.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import tony.side.domain.member.Member;

@Data
public class ChangeInfoDto {
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String eMail;
    @NotBlank
    private String phoneNumber;

    public Member toMember(String loginId) {
        return new Member(loginId, this.password, this.name, this.eMail, this.phoneNumber);
    }
}
