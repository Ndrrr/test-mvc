package athletetrainingprogram.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;
    private String email;
    private String password;

}
