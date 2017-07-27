package com.standings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by AaronPickar on 7/26/17.
 */
public class MultiBreaker {
    public ArrayList<Team> teams;

    public MultiBreaker(ArrayList<Team> teams) {
        this.teams = teams;
        orderTeams(this.teams);

    }
    public static HashMap<Team, Integer[]> orderTeams(ArrayList<Team> teams) {
        HashMap<Team, Integer[]> recordAgainst = new HashMap<>();
        for (int i = 0; i < teams.size(); i++) {
            Team t = teams.get(i);
            Integer[] winsGames = new Integer[2];
            for (int j = 0; j < teams.size(); j++) {
                if (j != i) {
                    winsGames[0] += t.getGamesAgainstTeam().get(teams.get(j))[0];
                    winsGames[1] += t.getGamesAgainstTeam().get(teams.get(j))[1];
                }
            }
            recordAgainst.put(t, winsGames);
        }
        return recordAgainst;
    }
}
