import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

import static spark.Spark.*;

public class ExternalAPIHandler {
    private String className = this.getClass().getName();
    private static final String API_URL = "http://jservice.io/api/random"; //"https://jsonplaceholder.typicode.com/posts"

    public QuestionCard getQuestionCard(int value) throws IOException, InterruptedException {
        //Skicka en /api/random-request till jService.io
        QuestionCard questionCard = new QuestionCard();
        questionCard.setDifficulty(value);

        //Här ska det väl ske ett anrop till jService och Pixabay antar jag


        //Test:
        //HttpClient client = HttpClient.newHttpClient();
        //HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(API_URL)).build();

        //TODO Funkar att hämta både Json och XML från jsonplaceholder men inte från jService. Får fortsätta försöka klura ut hur jService vill ha sina anrop.

        //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());

        JsonParser parser = new JsonParser(); //Kanske inte behöver skapa en ny varje gång?
        //JsonElement element = parser.parse(response.body());
        //JsonArray array = element.getAsJsonArray();
        //String answer = array.get(0).getAsString();

        //System.out.println("answer " + answer);
//        JsonObject pages = object.getAsJsonObject("answer");


//        System.out.println("pages: " + pages.getAsString());

        Gson gson = new Gson();
        //System.out.println(gson.toJson(element));
//        gson.toJson(object);
        //response.body();


        System.out.println("gson: " + gson);


        return questionCard;
    }

    //Den här metoden finns enbart för att testa ExternalAPIHandler-klassen
    public static void main(String[] args) {
        ExternalAPIHandler externalAPIHandler = new ExternalAPIHandler();
        port(80);

        get("/get", (req, res) -> {
            externalAPIHandler.getQuestionCard(200);
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
