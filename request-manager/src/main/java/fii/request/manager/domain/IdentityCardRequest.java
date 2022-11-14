package fii.request.manager.domain;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class IdentityCardRequest {
    String personalNumericCode;
    String firstName;
    String lastName;
    String fatherFirstName;
    String motherFirstName;
    LocalDate birthDate;
    String birthTown;
    String birthCountry;
    Adress currentAdress;
    Adress previousAdress;
    String phoneNumber;
    String previousName;
    String previousSchool;
    String profession;
    ArrayList<Child> children;
    String reason;
    LocalDate requestDate;
    Boolean isMale;
    Boolean isFemale;
    Boolean isSingle;
    Boolean isMarried;
    Boolean isDivorced;
    Boolean isWidow;
    Boolean isActive;
    Boolean isReservist;
    Boolean isRecruit;
    Boolean isWithoutMilitaryObligation;
}
