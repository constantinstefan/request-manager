package fii.request.manager.dto;

import lombok.Data;

@Data
public class AdressDto {
    String townName;
    String countryName;
    String streetName;
    String streetNumber;
    String buildingName;
    String entrance;
    String floorNumber;
    String apartment;
}
