

public class ExternalAPIHandler {
    private String className = this.getClass().getName();

    public QuestionCard getQuestionCard(int value) {
        //Skicka en /api/random-request till jService.io
        QuestionCard questionCard = new QuestionCard();
        questionCard.setDifficulty(value);

        //Här ska det väl ske ett anrop till jService och Pixabay antar jag
        return questionCard;
    }

    //Den här metoden finns enbart för att testa ExternalAPIHandler-klassen
    public static void main(String[] args) {

    }


}
