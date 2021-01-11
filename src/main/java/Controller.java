import java.util.ArrayList;

/**
 * Connects the database manager to the ExternalAPIHandler and the RunServer, the controller of the application
 *
 * @author Hanna My Jansson, Oscar Kareld
 * @version 1.0
 */
public class Controller {
    private DatabaseManager databaseManager;
    private FileManager fileManager;
    ExternalAPIHandler externalAPIHandler;

    public Controller() {
        databaseManager = new DatabaseManager();
        externalAPIHandler = new ExternalAPIHandler();
        fileManager = new FileManager();
        externalAPIHandler.start();
    }

    //gets the highscore from the database
    public ArrayList<Score> getHighScore(Difficulty difficulty, int nbrOfScores) {
        return fileManager.getHighScore(difficulty, nbrOfScores);
    }

    //adds a score to the database
    public void addScore(Score score) {
        fileManager.addScore(score);
    }


    //get a list of question cards depending on the difficulty
    public ArrayList<QuestionCard> getQuestionCards(Difficulty difficulty) {
        ArrayList<QuestionCard> list = new ArrayList<>();
        list = externalAPIHandler.getGameWithQuestionCards(difficulty);
        return list;
    }

}
