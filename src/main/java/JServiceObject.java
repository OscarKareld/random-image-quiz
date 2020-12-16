public class JServiceObject {
    private String className = this.getClass().getName();
    private String id;
    private String question;
    private String answer;
    private int difficulty;
    private String image;

    private String airdate;
    private String value;
    private String created_at;
    private String updated_at;
    private String category_id;
    private String game_id;
    private String invalid_count;
    private String category;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
