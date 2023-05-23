package fii.request.manager.dto;

import fii.request.manager.domain.Address;
import fii.request.manager.domain.Child;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
@Data
@Builder
public class IdentityCardRequestDto {
        @Size(min=13, max=13)
        String personalNumericCode;
        String firstName;
        String lastName;
        String sex;
        String fatherFirstName;
        String motherFirstName;
        LocalDate birthDate;
        String birthTown;
        String birthCountry;
        Address currentAdress;
        Address previousAdress;
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
