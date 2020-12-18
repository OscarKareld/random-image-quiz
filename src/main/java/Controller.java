import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private String className = this.getClass().getName();
    private DatabaseManager databaseManager;
    ExternalAPIHandler externalAPIHandler;

    public Controller() {
        databaseManager = new DatabaseManager();
        externalAPIHandler = new ExternalAPIHandler();
    }

    //gets the highscore from the database
    public ArrayList<Score> getHighScore(Difficulty difficulty) {
        return databaseManager.getHighScore(difficulty);
    }

    //adds a score to the database
    public void addScore(Score score) {
        databaseManager.addScore(score);
    }
    //get a list of question cards depending on the difficulty
    public ArrayList<QuestionCard> getQuestionCards(Difficulty difficulty) throws IOException, InterruptedException {
        ArrayList<QuestionCard> list = new ArrayList<>();
        externalAPIHandler.getGameWithQuestionCards(difficulty);
        return list;
    }

}
