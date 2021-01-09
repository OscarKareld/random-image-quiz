

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(highscoreEasy);
        Collections.sort(highscoreMedium);
        Collections.sort(highscoreDifficult);

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
/*
        score.setDifficulty("easy");
        score.setPoints(2010);
        score.setUserNickName("Oscar");
        fm.addScore(score);

        Score score1 = new Score();

        score1.setDifficulty("easy");
        score1.setPoints(5300);
        score1.setUserNickName("Oscar");
        fm.addScore(score1);

        Score score2 = new Score();

        score2.setDifficulty("easy");
        score2.setPoints(5900);
        score2.setUserNickName("Oscar");
        fm.addScore(score2);

        Score score3 = new Score();

        score3.setDifficulty("medium");
        score3.setPoints(4000);
        score3.setUserNickName("Oscar");
        fm.addScore(score3);

        Score score01 = new Score();

        score01.setDifficulty("easy");
        score01.setPoints(6010);
        score01.setUserNickName("Hanna");
        fm.addScore(score01);

        Score score21 = new Score();

        score21.setDifficulty("medium");
        score21.setPoints(4800);
        score21.setUserNickName("Hanna");
        fm.addScore(score);

        Score score11 = new Score();

        score11.setDifficulty("medium");
        score11.setPoints(7300);
        score11.setUserNickName("Hanna");
        fm.addScore(score11);

        Score score22 = new Score();

        score22.setDifficulty("difficult");
        score22.setPoints(1000);
        score22.setUserNickName("Hanna");
        fm.addScore(score);

        Score score23 = new Score();

        score23.setDifficulty("difficult");
        score23.setPoints(4000);
        score23.setUserNickName("Hanna");
        fm.addScore(score23);




        Score score7 = new Score();

        score7.setDifficulty("easy");
        score7.setPoints(3010);
        score7.setUserNickName("Hanna-My");
        fm.addScore(score7);

        Score score17 = new Score();

        score17.setDifficulty("medium");
        score17.setPoints(1300);
        score17.setUserNickName("Hanna-My");
        fm.addScore(score17);

        Score score27 = new Score();

        score27.setDifficulty("easy");
        score27.setPoints(2900);
        score27.setUserNickName("Hanna-My");
        fm.addScore(score27);

        Score score37 = new Score();

        score37.setDifficulty("medium");
        score37.setPoints(1250);
        score37.setUserNickName("Hanna-My");
        fm.addScore(score37);

        Score score177 = new Score();

        score177.setDifficulty("easy");
        score177.setPoints(8010);
        score177.setUserNickName("Rebecka");
        fm.addScore(score177);

        Score score217 = new Score();

        score217.setDifficulty("medium");
        score217.setPoints(7100);
        score217.setUserNickName("Rebecka");
        fm.addScore(score);

        Score score117 = new Score();

        score117.setDifficulty("medium");
        score117.setPoints(8900);
        score117.setUserNickName("Rebecka");
        fm.addScore(score117);

        Score score227 = new Score();
        score227.setDifficulty("difficult");
        score227.setPoints(7000);
        score227.setUserNickName("Rebecka");
        fm.addScore(score227);

        Score score237 = new Score();
        score237.setDifficulty("difficult");
        score237.setPoints(7200);
        score237.setUserNickName("Rebecka");
        fm.addScore(score237);

*/

      ArrayList<Score> a =  fm.getHighScore(Difficulty.medium, 12);

      for(int i = 0; i<a.size(); i++){
           System.out.println(a.get(i).getUserNickName() + " " + a.get(i).getPoints() + " poäng");
        }
    }


}
