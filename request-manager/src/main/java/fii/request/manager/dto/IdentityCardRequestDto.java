package fii.request.manager.dto;

import fii.request.manager.domain.Child;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
@Data
public class IdentityCardRequestDto {
        @Size(min=13, max=13)
        String personalNumericCode;
        String firstName;
        String lastName;
        @Pattern(regexp = "male|female")
        String sex;
        String fatherFirstName;
        String motherFirstName;
        LocalDate birthDate;
        String birthTown;
        String birthCountry;
        AdressDto currentAdress;
        AdressDto previousAdress;
        String phoneNumber;
        String previousName;
        @Pattern(regexp = "single|married|divorced|widow")
        String civilStatus;
        String militaryStatus;
        String previousSchool;
        String profession;
        ArrayList<Child> children;
        @Pattern(regexp="pierdere")
        String reason;
}
