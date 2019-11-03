package challenge.keywordscore.dto;

import java.util.List;

public class KeywordScoreResponse {
    private Integer score;
    private List<String> messages;

    public KeywordScoreResponse(Integer score, List<String> messages) {
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
}
