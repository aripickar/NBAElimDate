package com.standings;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class Game {
    private String date;
    private String HomeTeam;
    private String AwayTeam;
    private int HomeScore;
    private int AwayScore;
    private boolean HomeWon;
    public Game(String[] game) {
        date = game[0];
        HomeTeam = game[1];
        AwayTeam = game[2];
        HomeScore = Integer.parseInt(game[3]);
        AwayScore = Integer.parseInt(game[4]);
        if (game[5].equals("Home")) {
            HomeWon = true;
        } else {
            HomeWon = false;
        }
    }
    public String getDate() {
        return date;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public int getHomeScore() {
        return HomeScore;
    }
    public int getAwayScore() {
        return AwayScore;
    }

    public boolean HomeWon() {
        return HomeWon;
    }

}
