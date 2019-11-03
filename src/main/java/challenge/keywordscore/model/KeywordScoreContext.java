package challenge.keywordscore.model;

public class KeywordScoreContext {
    private Float score;
    private String keyword;
    private Boolean isFinished;

    public KeywordScoreContext(String keyword) {
        this.score = 100f;
        this.keyword = keyword;
        this.isFinished = false;
    }

    public Float getScore() {
        return score;
    }

    public Integer getScoreAsIntRound() {
        return Math.round(score);
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public void decreaseScore(Float value) {
        this.score -= value;
        if(this.score < 0f) {
            this.score = 0f;
        }
    }

    public void decreaseScoreByAbsentsFirstOccurenceIndex(Integer firstOccurrenceIndex) {
        float scoreProportionByKeywordLength = (100f / (float)keyword.length());
        float scoreProportionByIndex = (firstOccurrenceIndex != -1)
                ? (100 - (firstOccurrenceIndex.floatValue() * 10)) / 100
                : 1; // case if index not found
        float proportion = scoreProportionByKeywordLength * scoreProportionByIndex;

        System.out.print(" [proportion to decreace = "+proportion+"] ");

        decreaseScore(proportion);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "KeywordScoreContext{" +
                "score=" + score +
                ", keyword='" + keyword + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
