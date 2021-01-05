import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileManager {

    public void addScore(Score score) {

    }

    public ArrayList<Score> getHighScore(Difficulty difficulty, int nbrOfScores) {
return null;
    }


    public void saveToFile(LinkedList<Score> taskRegister){
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("files/taskregister")))){
            oos.writeObject(taskRegister);
            System.out.println("FileHandler: taskregister written: " + taskRegister.toString());
        }catch(FileNotFoundException e){
            System.out.println("FileHandler: File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public LinkedList<Score> readFromFile(){
        LinkedList<Score> highscore = null;
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/taskregister")))){
             highscore =  (LinkedList<Score>) ois.readObject();
            System.out.println("FileHandler: Task read: " + highscore.toString());
        }catch(FileNotFoundException e){
            System.out.println("FileHandler: File not found");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return highscore;
    }

}
