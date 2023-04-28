package athletetrainingprogram.dto.request;

import athletetrainingprogram.model.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateAthleteRequest {

    private String name;
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String address;

}
