package fii.request.manager.mapper;

import fii.request.manager.domain.Adress;
import fii.request.manager.dto.AdressDto;

import static fii.request.manager.util.StringUtil.toUpperCase;

public class AdressMapper {
    public static Adress map(AdressDto adressDto) {
        if(adressDto == null) return null;

        Adress adress = new Adress();

        adress.setTownName(toUpperCase(adressDto.getTownName()));
        adress.setStreetName(toUpperCase(adressDto.getStreetName()));
        adress.setStreetNumber(toUpperCase(adressDto.getStreetNumber()));
        adress.setBuildingName(toUpperCase(adressDto.getBuildingName()));
        adress.setFloorNumber(toUpperCase(adressDto.getFloorNumber()));
        adress.setEntrance(toUpperCase(adressDto.getEntrance()));
        adress.setApartment(toUpperCase(adressDto.getApartment()));

        return adress;
    }
}
