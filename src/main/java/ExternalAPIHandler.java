import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class ExternalAPIHandler {
    private String className = this.getClass().getName();
    private static final int nbrOfClues = 100;
    private static final String API_URL = "http://jservice.io/api/random";//"https://jsonplaceholder.typicode.com/posts";//; //
    private LinkedList<ArrayList<QuestionCard>> queueEasy = new LinkedList();
    private LinkedList<ArrayList<QuestionCard>> queueMedium = new LinkedList();
    private LinkedList<ArrayList<QuestionCard>> queueDifficult = new LinkedList();

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

        externalAPIHandler.getPicture("blue whale water");

    }

    //
    private String getPicture(String searchWord){

        HttpResponse<JsonNode> response;
        String pictureURL="";
        String searchString = searchWord;
        while(searchString.contains(" ")){
            int index = searchString.indexOf(" ");
            StringBuilder sb = new StringBuilder(searchString);
            sb.replace(index,index+1, "+");
            searchString = sb.toString();
            System.out.println(searchString);
        }
        try{
          response = Unirest.get("https://pixabay.com/api/")
                  .queryString("key","19377269-7e2c3f690ea34c13b2d506c0b")
                  .queryString("q",searchString)
                  .queryString("image_type","photo")
                  .asJson();

            JsonNode jsonNode = response.getBody();
            JSONObject jsonObject = jsonNode.getObject();
            JSONArray jsonArray = jsonObject.getJSONArray("hits");
            JSONObject jsonpicture = jsonArray.getJSONObject(0);


            System.out.println(jsonpicture);
            System.out.println(jsonpicture.getString("previewURL"));
            pictureURL = jsonpicture.getString("previewURL");
        }catch(Exception e){
            e.printStackTrace();
        }
        return pictureURL;
    }

    public ArrayList<QuestionCard> getGameWithQuestionCards(Difficulty difficulty){
        if(difficulty == Difficulty.easy){
            queueEasy.getFirst();
        }else if(difficulty == Difficulty.medium){

        }else if(difficulty == Difficulty.difficult){
            ArrayList<QuestionCard> =
        }
    }






}
