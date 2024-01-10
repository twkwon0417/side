package tony.side.web.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    @Size(min = 7, max = 12)
    private String loginId;

    @NotBlank
    @Size(min = 7, max = 12)
    private String password;
}
