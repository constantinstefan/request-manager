package fii.request.manager.service.entityextractor;

import fii.request.manager.service.entityextractor.domain.NamedEntityAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NamedEntityProvider {
    private HashMap<String, NamedEntityAttributes> namedEntities;

    @Autowired
    public NamedEntityProvider(HashMap<String, NamedEntityAttributes> namedEntities) {
        this.namedEntities = namedEntities;
    }

    public NamedEntityAttributes getEntityAttributes(String entityKey) {
        return namedEntities.get(entityKey);
    }

    public List<String> getAllEntityKeys() {
        return namedEntities.keySet().stream().toList();
    }

    public List<String> getAllEntityKeysInSameContext(String entityKey) {
        String contextKey = namedEntities.get(entityKey).getContextKey();
        List<String> result = namedEntities.keySet().stream()
                .filter(key -> namedEntities.get(key).getContextKey().equals(contextKey))
                .collect(Collectors.toList());
        return result;
    }
}
