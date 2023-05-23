package fii.request.manager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    String townName;
    String countryName;
    String streetName;
    String streetNumber;
    String buildingName;
    String entrance;
    String floorNumber;
    String apartment;
}
