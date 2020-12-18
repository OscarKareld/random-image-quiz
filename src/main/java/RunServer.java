import static spark.Spark.*;

import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.template.pebble.PebbleTemplateEngine;

import java.util.ArrayList;

//request.params("foo");
//response.type("text/xml");
//response.body("Hello");
//response.redirect("/bar");
//redirect.get("/fromPath", "/toPath");

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

        get("/result", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/result.html"));
        });

        get("/quiz", (req, res) -> {
            return new PebbleTemplateEngine().render(new ModelAndView(null, "templates/quiz.html"));
        });

        post("/save", (req, res) -> {
            //System.out.println(request.body()); // se vad post skickar oss
            return null;
        });
        get("/game/:diff", (req, res) -> {
            Difficulty difficulty = Difficulty.valueOf(req.params(":diff").toString());
            ArrayList<QuestionCard> game =  controller.getQuestionCards(difficulty);
            Gson gson = new Gson();
            String json =  gson.toJson(new String("test funkar detta"));
            res.header("Content-Type", "application/json");
            return json;
        });
    }
}
