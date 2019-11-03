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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class KeywordScoreController {

    @Autowired
    KeywordScoreService keywordScoreService;

    @RequestMapping("estimate")
    public ResponseEntity<KeywordScoreResponse> estimate(@RequestParam String keyword) {

        if(StringUtils.isEmpty(keyword)) {
            KeywordScoreResponse resp = new KeywordScoreResponse(null, Arrays.asList("Keyword should not be empty or blank"));
            return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
        } else if(keyword.contains(" ")){
            KeywordScoreResponse resp = new KeywordScoreResponse(null, Arrays.asList("Keyword should be a single word"));
            return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
        }

        Integer estimation = keywordScoreService.estimate(keyword);
        KeywordScoreResponse response = new KeywordScoreResponse(estimation, null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
