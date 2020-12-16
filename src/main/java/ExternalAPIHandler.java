import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class ExternalAPIHandler {
    private String className = this.getClass().getName();
    private static final int nbrOfClues = 100;
    private static final String API_URL = "http://jservice.io/api/random";//"https://jsonplaceholder.typicode.com/posts";//; //

    private void createGames() {
        /*
        Metoden skickar ett anrom (random) och tar emot 100 clues. Iterera igenom och samla clues:en i 3 arraylists (?).
        För varje clue i en arraylist görs ett anrop till bild-metoden.
        Arraylistsen sparas sedan i en kö, och så fort ett anrop görs till en tom kö, så görs ett nytt anrop till jService
         */


        HttpResponse<JsonNode> response = Unirest.get(API_URL)
//                .queryString("value", String.valueOf(value))
                .queryString("count", nbrOfClues)
                .asJson();

        JsonNode jsonNode = response.getBody();
        System.out.println(jsonNode);
        JSONArray jsonArray = jsonNode.getArray();
        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString("value") != null) {
                QuestionCard questionCard = new QuestionCard();

                questionCard.setId(jsonObject.getString("id"));
                questionCard.setAnswer(jsonObject.getString("answer"));
                questionCard.setQuestion(jsonObject.getString("question"));
                questionCard.setDifficulty(jsonObject.getInt("value"));
            }





            /*
            System.out.println(i + "\n Answer: " + jsonObject.getString("answer"));
            System.out.println("Question: " + jsonObject.getString("question"));
            System.out.println("Value: " + jsonObject.getString("value"));
            System.out.println();

             */

//            questionCard.setAnswer(jsonObject.getString("answer"));
        }





        //String metod(String answer) {
        //return URL;

    }

    //Den här metoden finns enbart för att testa ExternalAPIHandler-klassen
    public static void main(String[] args) {
        ExternalAPIHandler externalAPIHandler = new ExternalAPIHandler();
        externalAPIHandler.createGames();
    }


}
