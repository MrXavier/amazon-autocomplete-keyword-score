package challenge.keywordscore.service;

import challenge.keywordscore.client.AmazonClient;
import challenge.keywordscore.model.KeywordScoreContext;
import kong.unirest.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class KeywordScoreServiceImpl implements KeywordScoreService {

    public static final String SPACE = " ";

    @Autowired
    private AmazonClient amazonClient;

    @Override
    public Integer estimate(String keyword) {
        KeywordScoreContext keywordScore = new KeywordScoreContext(keyword);

        System.out.println(LocalTime.now() + " -- Starting keywordScore = " + keywordScore);
        for (int i = 0; i < keyword.length(); i++) {
            String prefix = keyword.substring(0, i + 1);
            JsonNode json = amazonClient.autocomplete(prefix);

            processKeywordScore(keywordScore, json);
            System.out.println(String.format(
                    " i = %s, prefix = %s, keywordScore = %s", i, prefix, keywordScore
            ));

            if(keywordScore.getFinished()){
                break;
            }
        }

        System.out.println(String.format(
                LocalTime.now() + " --- Finished keyScore. [score = %s] ---",
                keywordScore.getScore()));
        return keywordScore.getScoreAsIntRound();
    }

    // TODO Move bellow code to a class like "KeywordScoreProcessor" for better modularization and cohersion
    private void processKeywordScore(KeywordScoreContext keywordScore, JsonNode json) {
        List<String> suggestions = json.getArray().getJSONArray(1).toList();
        if(suggestions.size() == 0) {
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

        if(firstOccurrenceIndex == 0) {
            keywordScore.setFinished(true);
            return;
        }

        System.out.print(" [firstOccurrenceIndex = "+firstOccurrenceIndex+"] ");
        keywordScore.decreaseScoreByAbsentsFirstOccurenceIndex(firstOccurrenceIndex);
    }

    private boolean isKeywordMatchInSuggestion(String suggestion, String keyword) {
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
