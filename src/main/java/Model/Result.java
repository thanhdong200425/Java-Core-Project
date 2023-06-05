package Model;

public class Result {
    private int idMatches;
    private int idClbWin;
    private int idClbLose;
    private int score1;
    private int score2;
    public Result(){

    }

    public Result(int idMatches, int idClbWin, int idClbLose, int score1, int score2) {
        this.idMatches = idMatches;
        this.idClbWin = idClbWin;
        this.idClbLose = idClbLose;
        this.score1 = score1;
        this.score2 = score2;
    }

    public int getIdMatches() {
        return idMatches;
    }

    public void setIdMatches(int idMatches) {
        this.idMatches = idMatches;
    }

    public int getIdClbWin() {
        return idClbWin;
    }

    public void setIdClbWin(int idClbWin) {
        this.idClbWin = idClbWin;
    }

    public int getIdClbLose() {
        return idClbLose;
    }

    public void setIdClbLose(int idClbLose) {
        this.idClbLose = idClbLose;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}
