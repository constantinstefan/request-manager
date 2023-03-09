package fii.request.manager.service.entityextractor.domain;

import fii.request.manager.service.entityextractor.FuzzySearchAdapter;
import fii.request.manager.service.entityextractor.NamedEntityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityRegexBuilderFactory {
    private NamedEntityProvider namedEntityProvider;

    private FuzzySearchAdapter fuzzySearchAdapter;

    @Autowired
    public EntityRegexBuilderFactory(NamedEntityProvider namedEntityProvider,
                                     FuzzySearchAdapter fuzzySearchAdapter) {
        this.namedEntityProvider = namedEntityProvider;
        this.fuzzySearchAdapter = fuzzySearchAdapter;
    }

    public EntityRegex.EntityRegexBuilder createBuilder() {
        return EntityRegex.builder()
                .namedEntityProvider(namedEntityProvider)
                .fuzzySearchAdapter(fuzzySearchAdapter);
    }
}
