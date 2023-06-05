package Model;

public class Player {
    private int idPlayer;
    private int idClub;

    private String namePlayer;

    private int shirtNumber;

    private int goal;

    private int redCard;

    private int yellowCard;

    public Player(){

    }

    public Player(int idPlayer, int idClub, String namePlayer, int shirtNumber, int goal, int redCard, int yellowCard) {
        this.idPlayer = idPlayer;
        this.idClub = idClub;
        this.namePlayer = namePlayer;
        this.shirtNumber = shirtNumber;
        this.goal = goal;
        this.redCard = redCard;
        this.yellowCard = yellowCard;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getRedCard() {
        return redCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }
}
