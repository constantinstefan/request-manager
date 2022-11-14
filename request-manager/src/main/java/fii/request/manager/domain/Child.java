package fii.request.manager.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Child {
    String name;
    LocalDate birthDate;
    String birthPlace;
}
