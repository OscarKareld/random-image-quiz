import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private String className = this.getClass().getName();

    String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
            "databaseName=RIQ;user=RIQ_admin;password=RIQpassword;";

    public Connection connect() throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String user = "RIQ_admin";
        String password = "RIQpassword";

        String dbURL = "jdbc:sqlserver://localhost";
        try {
            Connection connection = DriverManager.getConnection(dbURL, user, password); //"jdbc: sqlserver://localhost:1433","RIQ_admin", "RIQpassword"
            if (connection != null) {
                System.out.println("connected");
                return connection;
//                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void testSelectQuery() {
        try { Connection connection = connect();
            Statement statement = connection.createStatement();
            String selectQuery = "select * from RIQ.dbo.Highscore";
            ResultSet results = statement.executeQuery(selectQuery);

            while (results.next()) {
                System.out.println(results.getString(1) + " " + results.getString(2)
                + " " + results.getString(3) + " " + results.getString(4));
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void addScore(Score score) {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();

            String addScoreQuery = "insert into RIQ.dbo.Highscore (nickname, points, date, difficulty)" +
                    " values ('" + score.getUserNickName() + "', " + score.getPoints() + ", '" + score.getDate() + "', '" + score.getDifficulty() + "');";
            System.out.println("addScoreQuery: " + addScoreQuery);

            statement.executeQuery(addScoreQuery);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Score> getHighScore(Difficulty difficulty) {
        ArrayList<Score> highScoreList = new ArrayList<>();

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();

            String getHighScoreQuery = "Select * from RIQ.dbo.Highscore where difficulty = '" + difficulty + "';";
            System.out.println("getHighscoreQuery: " + getHighScoreQuery);

            ResultSet results = statement.executeQuery(getHighScoreQuery);
            while (results.next()) {
                Score score = new Score();
                score.setUserNickName(results.getString("nickname"));
                score.setPoints(results.getInt("points"));
                score.setDate(results.getDate("date"));
                score.setDifficulty(results.getString("difficulty"));
                highScoreList.add(score);
                System.out.println(score.getUserNickName());
            }




        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return highScoreList;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        DatabaseManager dbm = new DatabaseManager();
        Score score = new Score();
        score.setUserNickName("Oscar");
        score.setPoints(10000);
        score.setDate(new Date(1000000)); //lol
        score.setDifficulty("easy");

//        dbm.addScore(score); //TODO: Fick nåt exception från SQL, men värdena lades in i tabellen ändå. Vid testning räcker det med att ändra userNickName.
//        dbm.testSelectQuery();
        dbm.getHighScore(Difficulty.easy);
    }
}
