package fii.request.manager.mapper;

import fii.request.manager.domain.DateString;
import fii.request.manager.domain.IdentityCardRequest;
import fii.request.manager.dto.IdentityCardRequestDto;

import java.time.LocalDate;

import static fii.request.manager.util.StringUtil.toUpperCase;

public class IdentityCardRequestMapper {

    public static IdentityCardRequest map(IdentityCardRequestDto identityCardRequestDto) {
        IdentityCardRequest identityCardRequest = new IdentityCardRequest();

        identityCardRequest.setPersonalNumericCode(identityCardRequestDto.getPersonalNumericCode());
        identityCardRequest.setFirstName(toUpperCase(identityCardRequestDto.getFirstName()));
        identityCardRequest.setLastName(toUpperCase(identityCardRequestDto.getLastName()));
        identityCardRequest.setBirthDate(DateString.fromLocalDate(identityCardRequestDto.getBirthDate()));
        identityCardRequest.setPhoneNumber(identityCardRequestDto.getPhoneNumber());
        identityCardRequest.setFatherFirstName(toUpperCase(identityCardRequestDto.getFatherFirstName()));
        identityCardRequest.setMotherFirstName(toUpperCase(identityCardRequestDto.getMotherFirstName()));
        identityCardRequest.setIsMale(identityCardRequestDto.getSex().equals("male"));
        identityCardRequest.setIsFemale(identityCardRequestDto.getSex().equals("female"));
        identityCardRequest.setIsSingle(identityCardRequestDto.getCivilStatus().equals("single"));
        identityCardRequest.setIsMarried(identityCardRequestDto.getCivilStatus().equals("married"));
        identityCardRequest.setIsDivorced(identityCardRequestDto.getCivilStatus().equals("divorced"));
        identityCardRequest.setIsWidow(identityCardRequestDto.getCivilStatus().equals("widow"));
        identityCardRequest.setIsActive(identityCardRequestDto.getMilitaryStatus().equals("active"));
        identityCardRequest.setIsReservist(identityCardRequestDto.getMilitaryStatus().equals("reservist"));
        identityCardRequest.setIsRecruit(identityCardRequestDto.getMilitaryStatus().equals("recruit"));
        identityCardRequest.setIsWithoutMilitaryObligation(identityCardRequestDto.getMilitaryStatus().equals("no-military-obligation"));
        identityCardRequest.setCurrentAddress(AdressFormatter.format(identityCardRequestDto.getCurrentAdress()));
        identityCardRequest.setPreviousAddress(AdressFormatter.format(identityCardRequestDto.getPreviousAdress()));
        identityCardRequest.setPreviousSchool(toUpperCase(identityCardRequestDto.getPreviousSchool()));
        identityCardRequest.setProfession(toUpperCase(identityCardRequestDto.getProfession()));
        identityCardRequest.setReason(toUpperCase(identityCardRequestDto.getReason()));
        identityCardRequest.setBirthTown(toUpperCase(identityCardRequestDto.getBirthTown()));
        identityCardRequest.setBirthCountry(toUpperCase(identityCardRequestDto.getBirthCountry()));
        identityCardRequest.setRequestDate(DateString.fromLocalDate(LocalDate.now()));
        System.out.println(identityCardRequest);
        return identityCardRequest;
    }
}
