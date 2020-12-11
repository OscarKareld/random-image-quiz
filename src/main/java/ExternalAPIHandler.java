

public class ExternalAPIHandler {
    private String className = this.getClass().getName();

    public String makeJServiceRequest() {
        //Skicka en /api/random-request till jService.io
        return className;
    }
}
