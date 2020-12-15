import static spark.Spark.*;
import spark.ModelAndView;
import spark.Request;
import spark.template.pebble.PebbleTemplateEngine;


//request.params("foo"); 
//response.type("text/xml");
//response.body("Hello"); 
//response.redirect("/bar");
//redirect.get("/fromPath", "/toPath");

public class RunServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(8080);

<<<<<<< HEAD
        staticFiles.location("/static");
        
        get("/", (req, res) -> {
            return new PebbleTemplateEngine().render(
            new ModelAndView(null, "templates/index.html"));
=======
        get("/get", (req, res) -> {
            return "Hej Oscar";
>>>>>>> bd4544a5e7f7126626e7c4f50b3903a054c238d9
        });

        get("/scoreboard", (req, res) -> {
            return new PebbleTemplateEngine().render(
            new ModelAndView(null, "templates/scoreboard.html"));
        });

        get("/api", (req, res) -> {
            return new PebbleTemplateEngine().render(
            new ModelAndView(null, "templates/api.html"));
        });

        get("/result", (req, res) -> {
            return new PebbleTemplateEngine().render(
            new ModelAndView(null, "templates/result.html"));
        });

        get("/question", (req, res) -> {
            return new PebbleTemplateEngine().render(
            new ModelAndView(null, "templates/question.html"));
        });        

    }
}
