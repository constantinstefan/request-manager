package fii.request.manager.service.entityextractor;

import fii.request.manager.service.entityextractor.domain.EntityRegex;
import fii.request.manager.service.entityextractor.domain.EntityRegexBuilderFactory;
import fii.request.manager.service.entityextractor.domain.NamedEntityAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityExtractorService {

    private FuzzySearchAdapter fuzzySearchAdapter;

    private EntityRegexBuilderFactory entityRegexBuilderFactory;

    private NamedEntityProvider namedEntityProvider;

    @Autowired
    public EntityExtractorService(FuzzySearchAdapter fuzzySearchAdapter,
                                  EntityRegexBuilderFactory entityRegexBuilderFactory,
                                  NamedEntityProvider namedEntityProvider) {
        this.fuzzySearchAdapter = fuzzySearchAdapter;
        this.entityRegexBuilderFactory = entityRegexBuilderFactory;
        this.namedEntityProvider = namedEntityProvider;
    }

    public String extractEntityFromText(String text, String entityKey) {
        final List<String> contextLines = buildContextLinesForEntityKey(text, entityKey);
        if(contextLines == null) {
            return null;
        }

       final EntityRegex entityRegex = entityRegexBuilderFactory.createBuilder()
               .contextLines(contextLines)
               .build();

       final String entity = entityRegex.find(entityKey);
       System.out.println("match: " + entity);
       return entity;
    }

    public HashMap<String, String> extractAllEntitiesFromTextInSameContext(String text, String entityKey) {
        final HashMap<String, String> entityKeyToResult = new HashMap<>();
        final List<String> contextLines = buildContextLinesForEntityKey(text, entityKey);
        if(contextLines == null) {
            return null;
        }

        final EntityRegex entityRegex = entityRegexBuilderFactory.createBuilder()
                .contextLines(contextLines)
                .build();

        namedEntityProvider.getAllEntityKeysInSameContext(entityKey).stream()
                .forEach(key -> entityKeyToResult.put(key, entityRegex.find(key)));
        System.out.println("match: " + entityKeyToResult);
        return entityKeyToResult;
    }

    private List<String> buildContextLinesForEntityKey(String text, String entityKey) {
        final NamedEntityAttributes entityAttributes = namedEntityProvider.getEntityAttributes(entityKey);
        if(entityAttributes == null) {
            return null;
        }
        final List<String> textLines = text.lines().collect(Collectors.toList());
        final String contextKey = entityAttributes.getContextKey();

        String context = fuzzySearchAdapter.fuzzySearch(textLines, contextKey);
        context = addNextLineToContext(textLines, context);
        context = addNextLineToContext(textLines, context);
        final List<String> contextLines = context.lines().collect(Collectors.toList());
        return contextLines;
    }

    private String addNextLineToContext(List<String> lines, String context) {
        final List<String> subtextLines = context.lines().collect(Collectors.toList());

        final int index = lines.indexOf(subtextLines.get(subtextLines.size()-1));
        if (index >= 0 && index < lines.size() - 1) {
            return context + "\n" + lines.get(index + 1);
        }
        return context;
    }
}
