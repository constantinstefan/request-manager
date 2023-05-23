package fii.request.manager.mapper;

import fii.request.manager.domain.Address;

import static fii.request.manager.service.helper.string.StringHelper.toUpperCase;

public class AdressFormatter {
    public static Address format(Address address) {
        if(address == null) return null;

        return Address.builder()
                .townName(toUpperCase(address.getTownName()))
                .countryName(toUpperCase(address.getCountryName()))
                .streetName(toUpperCase(address.getStreetName()))
                .streetNumber(toUpperCase(address.getStreetNumber()))
                .buildingName(toUpperCase(address.getBuildingName()))
                .floorNumber(toUpperCase(address.getFloorNumber()))
                .entrance(toUpperCase(address.getEntrance()))
                .apartment(toUpperCase(address.getApartment()))
                .build();
    }
}
