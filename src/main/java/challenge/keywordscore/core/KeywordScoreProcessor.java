package challenge.keywordscore.core;

import challenge.keywordscore.model.KeywordScoreContext;
import kong.unirest.JsonNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeywordScoreProcessor {

    public static final String SPACE = " ";

    /**
     * Calculate and adjust the score based on the Amazon's API suggestions. It's being done by taking the first occurrence
     * and use it to adjust the score with this number.
     *
     * @param keywordScore Object to hold the state of the keyword processing along with score and if it's finished.
     * @param json json returned from Amazon API with the suggestions
     */
    public void processKeywordScore(KeywordScoreContext keywordScore, JsonNode json) {
        List<String> suggestions = json.getArray().getJSONArray(1).toList();
        if(suggestions.size() == 0) { // if empty suggestions, keyword is not scored by Amazon
            keywordScore.setScore(0f);
            keywordScore.setFinished(true);
            return;
        }

        Integer firstOccurrenceIndex = -1;
        for (int i = 0; i < suggestions.size(); i++) {
            if(isKeywordMatchInSuggestion(suggestions.get(i), keywordScore.getKeyword())) {
                firstOccurrenceIndex = i;
                break;
            }
        }

        if(firstOccurrenceIndex == 0) { // If its the first occurrence it means next prefixes will get the same result
            keywordScore.setFinished(true);
            return;
        }

        System.out.print(" [firstOccurrenceIndex = "+firstOccurrenceIndex+"] ");
        keywordScore.decreaseScoreByAbsentsFirstOccurenceIndex(firstOccurrenceIndex);
    }

    public boolean isKeywordMatchInSuggestion(String suggestion, String keyword) {
        String[] keywordWords = keyword.split(SPACE);
        String[] suggestionWords = suggestion.split(SPACE);

        Boolean matched = false;
        int j = 0;
        for (int i = 0; i < suggestionWords.length; i++) {
            if(suggestionWords[i].equals(keywordWords[j])){
                j++;
                if(j == keywordWords.length){
                    matched = true;
                    break;
                }
            } else {
                j = 0;
            }
        }
        return matched;
    }
}
