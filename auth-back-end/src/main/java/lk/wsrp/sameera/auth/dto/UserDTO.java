package lk.wsrp.sameera.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    @Length(min = 4)
    private String password;
    @NotBlank(groups = SignUp.class)
    @Pattern(regexp = "[A-Za-z ]+", groups = SignUp.class)
    private String fullName;

    public interface SignUp extends Default{}
}
