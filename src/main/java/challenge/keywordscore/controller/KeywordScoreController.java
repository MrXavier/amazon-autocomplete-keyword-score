package challenge.keywordscore.controller;

import challenge.keywordscore.dto.KeywordScoreResponse;
import challenge.keywordscore.service.KeywordScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/")
public class KeywordScoreController {

    @Autowired
    KeywordScoreService keywordScoreService;

    @RequestMapping("estimate")
    public ResponseEntity<KeywordScoreResponse> estimate(@RequestParam String keyword) {
        if(StringUtils.isEmpty(keyword)) {
            KeywordScoreResponse resp = new KeywordScoreResponse(keyword, null, Arrays.asList("Keyword should not be empty or blank"));
            return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
        }

        keyword = keyword.toLowerCase();
        Integer estimation = keywordScoreService.estimate(keyword);

        KeywordScoreResponse response = new KeywordScoreResponse(keyword, estimation, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
