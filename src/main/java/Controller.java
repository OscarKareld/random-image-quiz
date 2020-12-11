public class Controller {
    private String className = this.getClass().getName();
    private DatabaseManager databaseManager;
    ExternalAPIHandler externalAPIHandler;

    public Controller() {
        databaseManager = new DatabaseManager();
        externalAPIHandler = new ExternalAPIHandler();
    }

    public void collectResponseFromJService() {
        System.out.println("Controllern tar emot: " + externalAPIHandler.makeJServiceRequest());
    }
}
