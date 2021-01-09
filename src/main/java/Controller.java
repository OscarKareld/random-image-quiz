import com.google.gson.Gson;

import java.util.ArrayList;
/**
 * Connects the database manager to the ExternalAPIHandler and the RunServer, the controller of the application
 * @author Hanna My Jansson, Oscar Kareld
 * @version 1.0
 */
public class Controller {
    private String className = this.getClass().getName();
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

    public static void main(String[] args) {
        System.out.println("här");
        Difficulty difficulty = Difficulty.valueOf("medium");
        System.out.println("här" + difficulty);
        Controller controller = new Controller();
        ArrayList<QuestionCard> game =  controller.getQuestionCards(difficulty);
        System.out.println(game.size() + "\n" + game.toString());
        Gson gson = new Gson();
        String json =  gson.toJson(game);
        System.out.println();
    }

}
