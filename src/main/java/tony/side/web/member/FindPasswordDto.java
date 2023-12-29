package tony.side.web.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FindPasswordDto {

    @NotBlank
    @Size(min = 7, max = 12)
    private String loginId;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phoneNumber;
}
