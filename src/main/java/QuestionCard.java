/**
 * The question card contains all the info regarding the questions, including the answer, the difficulty, the image
 * @author  Oscar Kareld
 * @version 1.0
 *
 */

public class QuestionCard {
    private String className = this.getClass().getName();
    private String id;
    private String question;
    private String answer;
    private int difficulty;
    private String image;

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
