package grauebcf.schoolapppro.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {

    @Email
    private String email;
    private String password;
//    private String confirmPassword;

    public UserRegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
