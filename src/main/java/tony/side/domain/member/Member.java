package tony.side.domain.member;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String loginId;
    private Long id;
    private String password;
    private String name;
    private String eMail;
    private LocalDate birthDate;
    private String phoneNumber;

    public Member() {
    }

    public Member(String loginId, String password, String name, String eMail, LocalDate birthDate, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.eMail = eMail;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
