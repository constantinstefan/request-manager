package fii.request.manager.service.entityextractor;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FuzzySearchAdapter {
    public String fuzzySearch(String doc, String toSearch) {
        return fuzzySearch(doc, toSearch, "\n");
    }

    public String fuzzySearch(String doc, String toSearch, String separator) {
        final List<String> tokens = List.of(doc.split(separator));
        return fuzzySearch(tokens, toSearch);
    }

    public String fuzzySearch(List<String> tokens, String toSearch) {

        final var result = FuzzySearch.extractTop(toSearch, tokens,1)
                .stream().findFirst().orElse(null);
        if(result == null) {
            return null;
        }

        //System.out.println("best score:" + result.getScore() + " string:" + result.getString());
        if(result.getScore() < 50) {
            return null;
        }

        return result.getString();
    }
}
