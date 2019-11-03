package challenge.keywordscore.service;

import challenge.keywordscore.client.AmazonClient;
import challenge.keywordscore.model.KeywordScoreContext;
import kong.unirest.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class KeywordScoreServiceImpl implements KeywordScoreService {

    public static final String SPACE_CHAR = " ";
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

    private void processKeywordScore(KeywordScoreContext keywordScore, JsonNode json){
        List<String> suggestions = json.getArray().getJSONArray(1).toList();

        Integer nOccurrencies = (int) suggestions.stream() // TODO: Improve the occurrencies match to accept multiple words
                .filter(e -> Arrays.asList(e.split(SPACE_CHAR)).contains(keywordScore.getKeyword()))
                .count();

        if(nOccurrencies == 10) {
            keywordScore.setFinished(true);
            return;
        }

        int nAbsents = 10 - nOccurrencies;
        System.out.print(" [nAbsents = "+nAbsents+"] ");
        keywordScore.decreaseScoreByAbsents(nAbsents);
    }
}
