package fii.request.manager.mapper;

import fii.request.manager.domain.Address;

import static fii.request.manager.util.StringUtil.toUpperCase;

public class AdressFormatter {
    public static Address format(Address address) {
        if(address == null) return null;

        Address newAddress = new Address();

        address.setTownName(toUpperCase(address.getTownName()));
        address.setCountryName(toUpperCase(address.getCountryName()));
        address.setStreetName(toUpperCase(address.getStreetName()));
        address.setStreetNumber(toUpperCase(address.getStreetNumber()));
        address.setBuildingName(toUpperCase(address.getBuildingName()));
        address.setFloorNumber(toUpperCase(address.getFloorNumber()));
        address.setEntrance(toUpperCase(address.getEntrance()));
        address.setApartment(toUpperCase(address.getApartment()));

        return address;
    }
}
