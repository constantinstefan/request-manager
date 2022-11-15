package fii.request.manager.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class IdentityCardRequest {
    String personalNumericCode;
    String firstName;
    String lastName;
    String fatherFirstName;
    String motherFirstName;
    DateString birthDate;
    String birthTown;
    String birthCountry;
    Address currentAddress;
    Address previousAddress;
    String phoneNumber;
    String previousName;
    String previousSchool;
    String profession;
    ArrayList<Child> children;
    String reason;
    DateString requestDate;
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
