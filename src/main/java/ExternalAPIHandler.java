import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class ExternalAPIHandler {
    private String className = this.getClass().getName();
    private static final String API_URL = "http://jservice.io/api/random";//"https://jsonplaceholder.typicode.com/posts";//; //

    public QuestionCard getQuestionCard(int value) {

        QuestionCard questionCard = new QuestionCard();
        questionCard.setDifficulty(value);

        HttpResponse<JsonNode> response = Unirest.get(API_URL)
                .queryString("value", String.valueOf(value))
                .asJson();

        System.out.println("URL: " + Unirest.get(API_URL)
                .queryString("value", String.valueOf(value))
                .getUrl());

        JsonNode jsonNode = response.getBody();
        JSONArray jsonArray = jsonNode.getArray();
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        System.out.println("Answer: " + jsonObject.getString("answer"));
        System.out.println("Question: " + jsonObject.getString("question"));
        System.out.println("Value: " + jsonObject.getString("value"));
        questionCard.setAnswer(jsonObject.getString("answer"));

        return questionCard;
    }

    //Den här metoden finns enbart för att testa ExternalAPIHandler-klassen
    public static void main(String[] args) {
        ExternalAPIHandler externalAPIHandler = new ExternalAPIHandler();
        externalAPIHandler.getQuestionCard(200);
    }


}
