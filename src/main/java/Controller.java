import com.google.gson.Gson;

import java.util.ArrayList;

public class Controller {
    private String className = this.getClass().getName();
    private DatabaseManager databaseManager;
    ExternalAPIHandler externalAPIHandler;

    public Controller() {
        databaseManager = new DatabaseManager();
        externalAPIHandler = new ExternalAPIHandler();
        externalAPIHandler.createGames();
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
