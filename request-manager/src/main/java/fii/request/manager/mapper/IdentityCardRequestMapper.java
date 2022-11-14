package fii.request.manager.mapper;

import fii.request.manager.domain.IdentityCardRequest;
import fii.request.manager.dto.IdentityCardRequestDto;

import javax.validation.Valid;

import static fii.request.manager.util.StringUtil.toUpperCase;

public class IdentityCardRequestMapper {

    public static IdentityCardRequest map(IdentityCardRequestDto identityCardRequestDto) {
        IdentityCardRequest identityCardRequest = new IdentityCardRequest();

        identityCardRequest.setPersonalNumericCode(identityCardRequestDto.getPersonalNumericCode());
        identityCardRequest.setFirstName(toUpperCase(identityCardRequestDto.getFirstName()));
        identityCardRequest.setLastName(toUpperCase(identityCardRequestDto.getLastName()));
        identityCardRequest.setBirthDate(identityCardRequestDto.getBirthDate());
        identityCardRequest.setFatherFirstName(toUpperCase(identityCardRequestDto.getFatherFirstName()));
        identityCardRequest.setMotherFirstName(toUpperCase(identityCardRequestDto.getMotherFirstName()));
        identityCardRequest.setIsMale(identityCardRequestDto.getSex().equals("male"));
        identityCardRequest.setIsMale(identityCardRequestDto.getSex().equals("female"));
        identityCardRequest.setIsActive(identityCardRequestDto.getMilitaryStatus().equals("active"));
        identityCardRequest.setIsReservist(identityCardRequestDto.getMilitaryStatus().equals("reservist"));
        identityCardRequest.setIsRecruit(identityCardRequestDto.getMilitaryStatus().equals("recruit"));
        identityCardRequest.setIsWithoutMilitaryObligation(identityCardRequestDto.getMilitaryStatus().equals("no-military-obligation"));
        identityCardRequest.setCurrentAdress(AdressMapper.map(identityCardRequestDto.getCurrentAdress()));
        identityCardRequest.setPreviousAdress(AdressMapper.map(identityCardRequestDto.getPreviousAdress()));
        identityCardRequest.setPreviousSchool(toUpperCase(identityCardRequestDto.getPreviousSchool()));
        identityCardRequest.setProfession(toUpperCase(identityCardRequest.getProfession()));
        identityCardRequest.setReason(toUpperCase(identityCardRequestDto.getReason()));
        identityCardRequest.setBirthTown(toUpperCase(identityCardRequestDto.getBirthTown()));
        identityCardRequest.setBirthCountry(toUpperCase(identityCardRequestDto.getBirthCountry()));

        return identityCardRequest;
    }
}
