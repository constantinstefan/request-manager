package fii.request.manager.domain;

import lombok.Data;

@Data
public class Adress {
    String townName;
    String countryName;
    String streetName;
    String streetNumber;
    String buildingName;
    String entrance;
    String floorNumber;
    String apartment;
}
