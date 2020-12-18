import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private String className = this.getClass().getName();
    private DatabaseManager databaseManager;
    ExternalAPIHandler externalAPIHandler;

    public Controller() {
        databaseManager = new DatabaseManager();
        externalAPIHandler = new ExternalAPIHandler();
    }

    //calculate value on question depending on the difficulty
    private int getValue(Difficulty difficulty){
        int value = 100;

        if(difficulty == Difficulty.easy){

            value = 200;

        }else if(difficulty == Difficulty.medium){
            value = 400;
        }else if(difficulty == Difficulty.difficult){
            value = 600;
        }
        return value;
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
    public ArrayList<QuestionCard> getQuestionCards(int amount, Difficulty difficulty) throws IOException, InterruptedException {
        ArrayList<QuestionCard> list = new ArrayList<>();
        for (int i = 0; i< amount; i++){
            list.add(externalAPIHandler.getQuestionCard(getValue(difficulty)));
        }
        return list;
    }

}
