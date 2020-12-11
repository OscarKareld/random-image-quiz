import static spark.Spark.*;

public class RunServer {

    public static void main(String[] args) {
        Controller controller = new Controller();
        port(80);

        get("/get", (req, res) -> {
            controller.collectResponseFromJService();
            return "Hej Oscar";
        });


        post("/", (req, res) -> {
            return "";
        });


        put("/:id", (req, res) -> {
            return "";
        });


        delete("/:id", (req, res) -> {
            return "";
        });
    }
}
