package challenge.keywordscore.dto;

import java.util.List;

public class KeywordScoreResponse {
    private String keyword;
    private Integer score;
    private List<String> messages;

    public KeywordScoreResponse(String keyword, Integer score, List<String> messages) {
        this.keyword = keyword;
        this.score = score;
        this.messages = messages;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addToMessages(String msg) {
        this.messages.add(msg);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
