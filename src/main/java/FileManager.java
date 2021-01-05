

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileManager {
    ArrayList<Score> highscoreEasy;
    ArrayList<Score> highscoreMedium;
    ArrayList<Score> highscoreDifficult;

    public FileManager(){
         highscoreEasy= new ArrayList<Score>();
         highscoreMedium= new ArrayList<Score>();
         highscoreDifficult = new ArrayList<Score>();
readFromFile();
    }
    public void addScore(Score score) {
        if(score.getDifficulty().equals("easy")){
            highscoreEasy.add(score);
        }else if(score.getDifficulty().equals("medium")){
            highscoreMedium.add(score);
        }else{
            highscoreDifficult.add(score);
        }
        saveToFile();
    }

    public ArrayList<Score> getHighScore(Difficulty difficulty, int nbrOfScores) {
        ArrayList<Score> highScore;
        if(difficulty.equals(Difficulty.easy)){

            highScore = new ArrayList<>( highscoreEasy.subList(0, Math.min(nbrOfScores, highscoreEasy.size())));
        }else if(difficulty.equals(Difficulty.medium)){
            highScore = new ArrayList<>( highscoreMedium.subList(0, Math.min(nbrOfScores, highscoreMedium.size())));
        }else{
            highScore = new ArrayList<>( highscoreDifficult.subList(0, Math.min(nbrOfScores, highscoreDifficult.size())));
        }
        return highScore;
    }


    public void saveToFile(){
        ArrayList<Score>[] saveObject= new ArrayList[3];
        saveObject[0] = highscoreEasy;
        saveObject[1] = highscoreMedium;
        saveObject[2] = highscoreDifficult;

        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("files/highscore.txt")))){
            oos.writeObject(saveObject);
            System.out.println("FileHandler: highscore written: " + saveObject.toString());
        }catch(FileNotFoundException e){
            System.out.println("FileHandler: File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void readFromFile(){
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/highscore.txt")))){
            ArrayList<Score>[] savedObject = (ArrayList<Score>[]) ois.readObject();
            if(savedObject != null) {
                highscoreEasy = savedObject[0];
                highscoreMedium = savedObject[1];
                highscoreDifficult = savedObject[2];
            }
        }catch(FileNotFoundException e){
            System.out.println("FileHandler: File not found");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ;
    }

    public static void main(String[] args) {
        FileManager fm = new FileManager();
        Score score = new Score();
        score.setDatetime("2021-01-01");
        score.setDifficulty("easy");
        score.setPoints(9010);
        score.setUserNickName("Oscar");
        fm.addScore(score);
      ArrayList<Score> a =  fm.getHighScore(Difficulty.easy, 10);

      for(int i = 0; i<a.size(); i++){
           System.out.println(a.get(i).getUserNickName() + " " + a.get(i).getPoints() + " poäng");
        }
    }


}
