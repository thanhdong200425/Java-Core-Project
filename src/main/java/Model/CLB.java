package Model;


public class CLB implements Comparable<CLB> {


    private int id;
    private String name;
    private int hlv;

    private int points;

    private int gamePlayed;

    public CLB(int id, String name, int hlv, int rank) {
        this.id = id;
        this.name = name;
        this.hlv = hlv;

    }





    public CLB() {
    }

    public CLB(int id, String name, int hlv, int points, int gamePlayed) {
        this.id = id;
        this.name = name;
        this.hlv = hlv;
        this.points = points;
        this.gamePlayed = gamePlayed;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHlv() {
        return hlv;
    }

    public void setHlv(int hlv) {
        this.hlv = hlv;
    }

    @Override
    public int compareTo(CLB o) {
        return this.id - o.id;
    }
}

