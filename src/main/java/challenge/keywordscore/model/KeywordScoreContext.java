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

    /**
     * It takes the firstOccurrenceIndex and calculate an adjustment to the score based on that number. The formula is:
     * (100 / keyword.lenth) * (100 - (firstOccurrenceIndex * 10))
     * The first part calculate the value to adjust the score based on the keyword length. This is done because we are
     * processing each letter at a time for the whole keyword.
     * The second part will calculate how much proportion of the previous adjustment value the score will be adjust
     * based on the position it first appears in the results.
     *
     * @param firstOccurrenceIndex in the amazon suggestions
     */
    public void decreaseScoreByAbsentsFirstOccurenceIndex(Integer firstOccurrenceIndex) {
        float scoreAdjustmentByKeywordLength = (100f / (float)keyword.length());
        float scoreProportionByIndex = (firstOccurrenceIndex != -1)
                ? (100 - (firstOccurrenceIndex.floatValue() * 10)) / 100
                : 1; // case if index not found
        float proportion = scoreAdjustmentByKeywordLength * scoreProportionByIndex;

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
