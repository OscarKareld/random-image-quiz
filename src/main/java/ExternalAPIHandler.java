import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Creates the questions to the API by connecting to two external APIs, pixabay and jservice.
 *
 * @author Hanna My Jansson, Oscar Kareld
 * @version 1.0
 */
public class ExternalAPIHandler {
    private static final int nbrOfClues = 100;
    private static final String API_URL = "http://jservice.io/api/random";
    private LinkedList<ArrayList<QuestionCard>> queueEasy = new LinkedList();
    private LinkedList<ArrayList<QuestionCard>> queueMedium = new LinkedList();
    private LinkedList<ArrayList<QuestionCard>> queueDifficult = new LinkedList();

    ArrayList<QuestionCard> easy = new ArrayList<>();
    ArrayList<QuestionCard> medium = new ArrayList<>();
    ArrayList<QuestionCard> difficult = new ArrayList<>();

    boolean threadWorking = false;

    public void start() {
        ApiThread apiThread = new ApiThread();
        apiThread.start();
    }

    /**
     * This methode calls jservice to get 100 questions and then sort it into games with 10 questions each
     * depending on the difficulty level. It ads it to the three queues. It also calls the get picture and cleanUpAnswer for every question.
     */
    public void createGames() {
        System.out.println("Creating more games...");
        HttpResponse<JsonNode> response = Unirest.get(API_URL)
                .queryString("count", nbrOfClues)
                .asJson();

        JsonNode jsonNode = response.getBody();
        JSONArray jsonArray = jsonNode.getArray();

        easy = new ArrayList<>();
        medium = new ArrayList<>();
        difficult = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (!jsonObject.isNull("value")) {
                QuestionCard questionCard = new QuestionCard();
                String answer = jsonObject.getString("answer");
                answer = cleanUpAnswer(answer);
                String image = getPicture(answer);

                if (image == null) {
                    continue;
                }

                int difficulty = jsonObject.getInt("value");
                questionCard.setId(jsonObject.getString("id"));
                questionCard.setAnswer(answer);
                questionCard.setQuestion(jsonObject.getString("question"));
                questionCard.setDifficulty(difficulty);
                questionCard.setImage(image);
                if (difficulty > 0 && difficulty < 350) {
                    easy.add(questionCard);
                } else if (difficulty > 350 && difficulty < 650) {
                    medium.add(questionCard);
                } else if (difficulty > 650 && difficulty < 1100) {
                    difficult.add(questionCard);
                }
            }
            if (easy.size() == 10) {
                queueEasy.addLast(easy);
                easy = new ArrayList<>();
                System.out.println("Easy game added to queue");
            } else if (medium.size() == 10) {
                queueMedium.addLast(medium);
                medium = new ArrayList<>();
                System.out.println("Medium game added to queue");
            } else if (difficult.size() == 10) {
                queueDifficult.addLast(difficult);
                difficult = new ArrayList<>();
                System.out.println("Difficult game added to queue");
            }


        }

    }


    /**
     * Removes any unwanted parts of strings. It removes parenthesis ands it content, <> and its content, quotation marks and backslash
     *
     * @param searchWord the string you want to clean up
     * @return the cleaned string
     */
    private String cleanUpAnswer(String searchWord) {
        String searchString = searchWord;
        //System.out.println("dirty: " + searchString);
        searchString = searchString.toLowerCase();
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
        while (searchString.contains("\'")) {
            int index1 = searchString.indexOf("\'");
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
        while (searchString.contains(".")) {
            int index1 = searchString.indexOf(".");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();

        }
        while (searchString.contains(",")) {
            int index1 = searchString.indexOf(",");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();

        }

        while (searchString.contains(":")) {
            int index1 = searchString.indexOf(":");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();
        }
        while (searchString.contains("?")) {
            int index1 = searchString.indexOf("?");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();
        }
        while (searchString.contains("!")) {
            int index1 = searchString.indexOf("!");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();
        }
        while (searchString.contains("-")) {
            int index1 = searchString.indexOf("-");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();
        }

        while (searchString.contains("  ")) {
            int index1 = searchString.indexOf("  ");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();

        }
        while (searchString.contains("/")) {
            int index1 = searchString.indexOf("/");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, searchString.length());
            searchString = sb.toString();
        }

        while (searchString.endsWith(" ")) {
            searchString = searchString.substring(0, searchString.length() - 1);
        }
        while (searchString.startsWith(" ")) {
            searchString = searchString.substring(1);
        }

        return searchString;
    }

    /**
     * Search for pictures for every question, if it does not find it for the whole string it will divide it to smaller parts, first word by word then even smaller.
     *
     * @param searchWord the word thet you whant to sersh for a matching picture to.
     * @return the url as a string with the picture asked for.
     */
    private String getPicture(String searchWord) {

        HttpResponse<JsonNode> response;
        String pictureURL = null;
        String searchString = searchWord;
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
        while (searchString.contains("  ")) {
            int index1 = searchString.indexOf("  ");
            StringBuilder sb = new StringBuilder(searchString);
            sb.delete(index1, index1 + 1);
            searchString = sb.toString();
        }

        while (searchString.contains(" ")) {
            int index = searchString.indexOf(" ");
            StringBuilder sb = new StringBuilder(searchString);
            sb.replace(index, index + 1, "+");
            searchString = sb.toString();
        }

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
            pictureURL = jsonpicture.getString("webformatURL");
        } catch (Exception e) {
        }

        String saveorigninalSearchString = searchString;
        while (searchString != "" && pictureURL == null) {

            String searchStringPart = "";
            int index = searchString.indexOf("+");
            if (index != -1) {
                searchStringPart = searchString.substring(0, index);
                searchString = searchString.substring(index + 1);
            } else {
                searchStringPart = searchString;
                searchString = "";
            }

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
                pictureURL = jsonpicture.getString("webformatURL");
            } catch (Exception e) {
            }

        }
        searchString = saveorigninalSearchString;
        while (searchString != "" && pictureURL == null) {
            String searchStringPart = "";
            while (searchString.contains("+")) {
                int index = searchString.indexOf("+");
                StringBuilder sb = new StringBuilder(searchString);
                sb.delete(index, index + 1);
                searchString = sb.toString();
            }

            searchString = searchString.substring(0, searchString.length() - 1);

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
                pictureURL = jsonpicture.getString("webformatURL");
            } catch (Exception e) {
            }
        }
        if (pictureURL == null) {
            System.err.println("Ingen bild hittades");
        }
        return pictureURL;
    }

    /**
     * Gets a game from the correct queue and returns it
     *
     * @param difficulty the difficulty of the questions wanted, there are three: easy, medium, difficult
     * @return an Arraylist with 10 questioncards with the difficulty asked for.
     */
    public ArrayList<QuestionCard> getGameWithQuestionCards(Difficulty difficulty) {
        ArrayList<QuestionCard> game = null;

        if (difficulty == Difficulty.easy && !queueEasy.isEmpty()) {
            game = queueEasy.pollFirst();
        } else if (difficulty == Difficulty.medium && !queueMedium.isEmpty()) {
            game = queueMedium.pollFirst();
        } else if (difficulty == Difficulty.difficult && !queueDifficult.isEmpty()) {
            game = queueDifficult.pollFirst();

        }
        if ((queueEasy.size() <= 1 || queueMedium.size() <= 1 || queueDifficult.size() <= 1) && !threadWorking) {
            ApiThread apiThread = new ApiThread();
            apiThread.start();
        }
        return game;
    }

    /**
     * This thread is used when connecting to the apis to get questions and pictures to speed up our response time as a API
     */
    private class ApiThread extends Thread {

        public void run() {
            threadWorking = true;
            while (queueEasy.size() <= 1 || queueMedium.size() <= 1 || queueDifficult.size() <= 1) {
                createGames();
            }
            threadWorking = false;
        }
    }

}
