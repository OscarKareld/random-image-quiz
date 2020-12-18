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

        HttpResponse<JsonNode> response = Unirest.get(API_URL)
                .queryString("count", nbrOfClues)
                .asJson();

        JsonNode jsonNode = response.getBody();
        System.out.println(jsonNode);
        JSONArray jsonArray = jsonNode.getArray();

        ArrayList<QuestionCard> easy = new ArrayList<>();
        ArrayList<QuestionCard> medium = new ArrayList<>();
        ArrayList<QuestionCard> hard = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (!jsonObject.isNull("value")) {
                QuestionCard questionCard = new QuestionCard();
                String answer = jsonObject.getString("answer");
                String image = getPicture(answer);

                if (image == null) {
                    System.out.println("Image is null" + 1);
                    continue;
                }
                int difficulty = jsonObject.getInt("value");
                questionCard.setId(jsonObject.getString("id"));
                questionCard.setAnswer(answer);
                questionCard.setQuestion(jsonObject.getString("question"));
                questionCard.setDifficulty(difficulty);
                questionCard.setImage(image); //TODO Hehe den här kaosar
                if (difficulty > 0 && difficulty < 350) {
                    easy.add(questionCard);
                } else if (difficulty > 350 && difficulty < 650) {
                    medium.add(questionCard);
                } else if (difficulty > 650 && difficulty < 1100) {
                    hard.add(questionCard);
                }
            }
            if (easy.size() == 10) {
                queueEasy.add(easy);
                easy.clear();
                System.out.println("Easy game added to queue");
            } else if (medium.size() == 10) {
                queueMedium.add(medium);
                medium.clear();
                System.out.println("Medium game added to queue");
            } else if (hard.size() == 10) {
                queueDifficult.add(hard);
                hard.clear();
                System.out.println("Hard game added to queue");
            }
        }
    }

    //Den här metoden finns enbart för att testa ExternalAPIHandler-klassen
    public static void main(String[] args) {
        ExternalAPIHandler externalAPIHandler = new ExternalAPIHandler();
        externalAPIHandler.createGames();
//        externalAPIHandler.getPicture("blue whale water");

    }

    //
    private String getPicture(String searchWord) {

        HttpResponse<JsonNode> response;
        String pictureURL = null;
        String searchString = searchWord;
        System.out.println(searchString);
        while (searchString.contains("(")) {
            int index1 = searchString.indexOf("(");
            int index2 = searchString.indexOf(")");
            StringBuilder sb = new StringBuilder(searchString);
            if (index2 != -1) {
                sb.delete(index1, index2 + 1);
                searchString = sb.toString();
            } else {
                sb.delete(index1, index1 + 1);
                searchString = sb.toString();
            }
        }
        while (searchString.contains("<")) {
            int index1 = searchString.indexOf("<");
            int index2 = searchString.indexOf(">");
            StringBuilder sb = new StringBuilder(searchString);
            if (index2 != -1) {
                sb.delete(index1, index2 + 1);
                searchString = sb.toString();
            } else {
                sb.delete(index1, index1 + 1);
                searchString = sb.toString();
            }
        }
        while (searchString.contains("\"")) {
            int index1 = searchString.indexOf("\"");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();

        }
        while (searchString.contains("\\")) {
            int index1 = searchString.indexOf("\\");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();

        }
        if (searchString.startsWith("a ") || searchString.startsWith("A ")) {
            searchString = searchString.substring(2);
        }
        if (searchString.startsWith("the ") || searchString.startsWith("The ")) {
            searchString = searchString.substring(4);
        }
        while (searchString.endsWith(" ")) {
            searchString = searchString.substring(0, searchString.length() - 1);
        }
        while (searchString.startsWith(" ")) {
            searchString = searchString.substring(1);
        }
        System.out.println(searchString);
        while (searchString.contains(" ")) {
            int index = searchString.indexOf(" ");
            StringBuilder sb = new StringBuilder(searchString);
            sb.replace(index, index + 1, "+");
            searchString = sb.toString();
        }
        System.out.println(searchString);

        try {
            response = Unirest.get("https://pixabay.com/api/")
                    .queryString("key", "19377269-7e2c3f690ea34c13b2d506c0b")
                    .queryString("q", searchString)
                    .queryString("image_type", "photo")
                    .asJson();

            JsonNode jsonNode = response.getBody();
            JSONObject jsonObject = jsonNode.getObject();
            JSONArray jsonArray = jsonObject.getJSONArray("hits");
            JSONObject jsonpicture = jsonArray.getJSONObject(0);
            System.out.println(jsonpicture.getString("previewURL"));
            pictureURL = jsonpicture.getString("previewURL");
        } catch (Exception e) {
            System.out.println("hittar inget: " + pictureURL);
        }

        String saveorigninalSearchString = searchString;
        while (searchString != "" && pictureURL == null) {
            System.out.println("cont search with individual words");
            String searchStringPart = "";
            int index = searchString.indexOf("+");
            if (index != -1) {
                searchStringPart = searchString.substring(0, index);
                searchString = searchString.substring(index + 1);
            } else {
                searchStringPart = searchString;
                searchString = "";
            }

            System.out.println("SÖKER på: " + searchStringPart);
            try {
                response = Unirest.get("https://pixabay.com/api/")
                        .queryString("key", "19377269-7e2c3f690ea34c13b2d506c0b")
                        .queryString("q", searchStringPart)
                        .queryString("image_type", "photo")
                        .asJson();

                JsonNode jsonNode = response.getBody();
                JSONObject jsonObject = jsonNode.getObject();
                JSONArray jsonArray = jsonObject.getJSONArray("hits");
                JSONObject jsonpicture = jsonArray.getJSONObject(0);
                System.out.println(jsonpicture.getString("previewURL"));
                pictureURL = jsonpicture.getString("previewURL");
            } catch (Exception e) {
                System.out.println("Hittar fortfarande inget ");
            }

        }
        searchString = saveorigninalSearchString;
        System.out.println(searchString);
        while (searchString != "" && pictureURL == null) {
            System.out.println("cont search with shorter and shorter words");
            String searchStringPart = "";
            while (searchString.contains("+")) {
                int index = searchString.indexOf("+");
                StringBuilder sb = new StringBuilder(searchString);
                sb.delete(index, index + 1);
                searchString = sb.toString();
                System.out.println(searchString);
            }

            searchString = searchString.substring(0, searchString.length() - 1);

            System.out.println("SÖKER på: " + searchString);
            try {
                response = Unirest.get("https://pixabay.com/api/")
                        .queryString("key", "19377269-7e2c3f690ea34c13b2d506c0b")
                        .queryString("q", searchString)
                        .queryString("image_type", "photo")
                        .asJson();

                JsonNode jsonNode = response.getBody();
                JSONObject jsonObject = jsonNode.getObject();
                JSONArray jsonArray = jsonObject.getJSONArray("hits");
                JSONObject jsonpicture = jsonArray.getJSONObject(0);
                System.out.println(jsonpicture.getString("previewURL"));
                pictureURL = jsonpicture.getString("previewURL");
            } catch (Exception e) {
                System.out.println("Hittar fortfarande inget ");
            }


        }
        if (pictureURL == null) {
            System.err.println("FEEEEEEEL inget kunde hittas ");
        }
        System.out.println("return");
        return pictureURL;
    }

    public ArrayList<QuestionCard> getGameWithQuestionCards(Difficulty difficulty) {
        ArrayList<QuestionCard> game = null;


        if (difficulty == Difficulty.easy) {
            game = queueEasy.getFirst();
        } else if (difficulty == Difficulty.medium) {
            game = queueMedium.getFirst();
        } else if (difficulty == Difficulty.difficult) {
            game = queueDifficult.getFirst();

        }
        while (game == null) {
            createGames();
            if (difficulty == Difficulty.easy) {
                game = queueEasy.getFirst();
            } else if (difficulty == Difficulty.medium) {
                game = queueMedium.getFirst();
            } else if (difficulty == Difficulty.difficult) {
                game = queueDifficult.getFirst();

            }
        }
        return game;
    }
}
