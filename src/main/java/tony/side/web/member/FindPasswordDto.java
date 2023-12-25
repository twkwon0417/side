package tony.side.web.member;

import lombok.Data;

@Data
public class FindPasswordDto {
    private String loginId;
    private String phoneNumber;
}
