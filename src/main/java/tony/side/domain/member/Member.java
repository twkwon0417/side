package tony.side.domain.member;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Member {
    private String loginId;
    private Long id;
    private String password;
    private String name;
    private String eMail;
    private LocalDate birthDate;
    private String phoneNumber;

    public void setId(Long id) {
        this.id = id;
    }
}
