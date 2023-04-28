package athletetrainingprogram.dto.response;

import athletetrainingprogram.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UsersResponse {

    List<UserDto> users;

}
