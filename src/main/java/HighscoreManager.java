import java.io.*;
import java.util.ArrayList;

public class HighscoreManager {

    public void readHighscores() throws FileNotFoundException {
        BufferedReader r =
                new BufferedReader(new InputStreamReader(new FileInputStream("files/highscores")));
        ArrayList<String> words = new ArrayList<String>();
        while (true) {
            String word = null;
            try {
                word = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (word == null) {
                break;
            }
            words.add(word);
        }
        System.out.println(words);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new HighscoreManager().readHighscores();
    }

}
