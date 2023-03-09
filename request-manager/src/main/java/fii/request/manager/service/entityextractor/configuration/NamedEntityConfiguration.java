package fii.request.manager.service.entityextractor.configuration;

import fii.request.manager.service.entityextractor.domain.NamedEntityAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class NamedEntityConfiguration {

    @Bean
    HashMap<String, NamedEntityAttributes> namedEntities() {
        HashMap<String , NamedEntityAttributes> namedEntities = new HashMap<>();
        namedEntities.put("cnp", new NamedEntityAttributes("CNP", "\\d{13}"));
        namedEntities.put("bd", new NamedEntityAttributes("Bd", "\\w+(\\s(?!\\n)+\\w+)*"));
        namedEntities.put("nr", new NamedEntityAttributes("Bd", "\\d{1,3}"));
        namedEntities.put("bl", new NamedEntityAttributes("Bd", "[\\w\\d]+"));
        namedEntities.put("sc", new NamedEntityAttributes("Bd", "[A-Z]"));
        namedEntities.put("et", new NamedEntityAttributes("Bd", "[\\w\\d]+"));
        namedEntities.put("ap", new NamedEntityAttributes("Bd", "[\\w\\d]+"));
        namedEntities.put("Nume/Nom/Last name", new NamedEntityAttributes("Nume/Nom/Last name", "[A-Z]+"));
        namedEntities.put("Prenume/Prenom/First name", new NamedEntityAttributes("Prenume/Prenom/First name", "[A-Z-]+"));
        namedEntities.put("Jud", new NamedEntityAttributes("Jud", "[A-Z]{2}"));
        namedEntities.put("Mun", new NamedEntityAttributes("Jud", "\\w+(\\s(?!\\n)+\\w+)*"));
        namedEntities.put("CARTE DE IDENTITATE", new NamedEntityAttributes("CARTE DE IDENTITATE", "\\w{2} NR \\d{6}"));
        return namedEntities;
    }
}
