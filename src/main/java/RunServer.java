import static spark.Spark.*;

import com.google.gson.Gson;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import java.util.ArrayList;

//request.params("foo");
//response.type("text/xml");
//response.body("Hello");
//response.redirect("/bar");
//redirect.get("/fromPath", "/toPath");

/**
 * This is the serverpart that holds the connection and make it possible to make
 * requests.
 * 
 * @author Oscar Kareld, Hanna My Jansson, Hanna Nilsson, Rebecka Persson
 * @version 1.0
 *
 */
public class RunServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(8080);
        staticFiles.location("/static");

        get("/", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/index.html"));
        });

        get("/scoreboard", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/scoreboard.html"));
        });

        get("/api", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/api.html"));
        });

        get("/quiz/:diff", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/quiz.html"));
        });

        post("/save", (req, res) -> {
            // System.out.println(request.body()); // se vad post skickar oss
            return null;
        });
        get("/game/:diff", (req, res) -> {
            System.out.println("här");
            Difficulty difficulty = Difficulty.valueOf(req.params(":diff").toString());
            System.out.println("här");
            ArrayList<QuestionCard> game = controller.getQuestionCards(difficulty);
            Gson gson = new Gson();
            String json = gson.toJson(game);
            res.header("Content-Type", "application/json");
            return json;
        });

        get("/highscore", (req, res) -> { // http://localhost:8080/highscore?amount=1
            String stringAmount = req.queryMap().value("amount");
            int amount;
            if (stringAmount == null) {
                amount = 100;
            } else {
                amount = Integer.parseInt(stringAmount);
            }
            ArrayList<Score> highScoreEasy = controller.getHighScore(Difficulty.easy, amount);
            ArrayList<Score> highScoreMedium = controller.getHighScore(Difficulty.medium, amount);
            ArrayList<Score> highScoreDifficult = controller.getHighScore(Difficulty.difficult, amount);
            ArrayList<Score>[] lista = new ArrayList[3];
            lista[0] = highScoreEasy;
            lista[1] = highScoreMedium;
            lista[2] = highScoreDifficult;
            Gson gson = new Gson();
            String json = gson.toJson(lista);
            res.header("Content-Type", "application/json");
            return json;
        });

        post("/score", (req, res) -> {
            String json = req.body();
            // System.out.println(json);
            Gson gson = new Gson();
            Score score = gson.fromJson(json, Score.class); // TODO: Kolla med Hanna och Rebecka hur deras JSOn-objekt
                                                            // är konstruerat. Vi ändrade Date från Date.SQL till String
                                                            // i Score.
            controller.addScore(score);
            return json;
        });
    }
}
