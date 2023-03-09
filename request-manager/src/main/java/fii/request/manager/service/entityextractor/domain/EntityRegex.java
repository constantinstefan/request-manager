package fii.request.manager.service.entityextractor.domain;

import fii.request.manager.service.entityextractor.FuzzySearchAdapter;
import fii.request.manager.service.entityextractor.NamedEntityProvider;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Builder
public class EntityRegex {
    @NonNull
    private List<String> contextLines;

    @NonNull
    private NamedEntityProvider namedEntityProvider;

    @NonNull
    private FuzzySearchAdapter fuzzySearchAdapter;

    public String find(String entityKey) {
        String context = contextLines.stream().collect(Collectors.joining("\n"));
        String regex = buildEntityRegex(entityKey);
        if(regex == null) {
            return "regex failed";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(context);
        if(!matcher.find()){
            return "not found";
        }
        System.out.println("found "+ matcher.group());
        removeTextFromContextLines(contextLines, matcher.group());
        return matcher.group(1);
    }

    private String buildEntityRegex(String entityKey) {
        NamedEntityAttributes entityAttributes = namedEntityProvider.getEntityAttributes(entityKey);
        if(entityAttributes == null) {
            return null;
        }

        List<String> allKnownEntityKeysList = namedEntityProvider.getAllEntityKeys();
        String regex = String.format("%s[\\s\\S]*?(%s)\\s*(?=(?:%s|\\r?\\n|$))",
                getFuzzyEntityKey(entityKey),
                entityAttributes.getRegex(),
                allKnownEntityKeysList.stream()
                        .filter(key-> namedEntityProvider.getEntityAttributes(key).getContextKey().equals(entityAttributes.getContextKey()))
                        .map(key -> getFuzzyEntityKey(key))
                        .collect(Collectors.joining("|"))
        );

        System.out.println("<getEntityRegex> " + regex);
        return regex;
    }

    private String getFuzzyEntityKey(String key) {
        boolean containsSpace = key.contains(" ");

        String bestMatch= fuzzySearchAdapter.fuzzySearch(contextLines, key);
        if(bestMatch == null) {
            // to be checked
            return key;
        }
        if(!containsSpace) {
            bestMatch = fuzzySearchAdapter.fuzzySearch(bestMatch, key, "[ .]");
        }
        if(bestMatch.contains(key)) {
            return key;
        }
        return bestMatch;
    }

    private void removeTextFromContextLines(List<String> contextLines, String textToRemove) {
        textToRemove.lines().forEach(line -> {
            contextLines.stream().filter(contextLine -> contextLine.contains(line))
                    .findFirst().ifPresent(contextLine-> {
                        String newLine = contextLine.replace(textToRemove,"");
                        contextLines.set(contextLines.indexOf(contextLine), newLine);
                    });
            });
    }
}
