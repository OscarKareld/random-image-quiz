import java.io.Serializable;
import java.sql.Date;
/**
 * Teh score keeps track of a score connected to one game played by one person.
 * it stores the name, date, difficulty and nbr of points.
 * @author  Oscar Kareld
 * @version 1.0
 *
 */
public class Score implements Serializable, Comparable<Score> {
    private String className = this.getClass().getName();
    private String userNickName;
    private int points;
    private String datetime;
    private String difficulty;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    @Override
    public int compareTo(Score o) {
        if(points > o.points) {
            return -1;
        }else if(points == o.points){
            return 0;
        }else{
            return 1;
        }
    }
}
