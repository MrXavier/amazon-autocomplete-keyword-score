package challenge.keywordscore.service;

import challenge.keywordscore.client.AmazonClient;
import challenge.keywordscore.core.KeywordScoreProcessor;
import challenge.keywordscore.model.KeywordScoreContext;
import kong.unirest.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class KeywordScoreServiceImpl implements KeywordScoreService {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private KeywordScoreProcessor keywordScoreProcessor;


    /**
     * This method estimate the score of the given keyword by taking the resulted suggestions of each prefix of the keyword
     * and send it to the keywordScoreProcessor which will make the adjustments in score based on the resulted suggestions.
     *
     * @param keyword to be evaluated
     * @return integer with the estimation score of the given keyword
     */
    @Override
    public Integer estimate(String keyword) {
        KeywordScoreContext keywordScore = new KeywordScoreContext(keyword);

        System.out.println(LocalTime.now() + " -- Starting keywordScore = " + keywordScore);
        for (int i = 0; i < keyword.length(); i++) { // for every prefix of the keyword
            String prefix = keyword.substring(0, i + 1); // get the prefix starting with one letter and finishing with entire keyword
            JsonNode json = amazonClient.autocomplete(prefix); // get the suggestions from autocomplete Amazon API for each prefix

            keywordScoreProcessor.processKeywordScore(keywordScore, json);
            System.out.println(String.format(" i = %s, prefix = %s, keywordScore = %s", i, prefix, keywordScore));

            if(keywordScore.getFinished()){
                break;
            }
        }

        System.out.println(String.format(LocalTime.now() + " --- Finished keyScore. [score = %s] ---", keywordScore.getScore()));
        return keywordScore.getScoreAsIntRound();
    }

}
