package com.standings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class Division {
    public String Name;
    public PriorityQueue<Team> standings;
    private ArrayList<Team> teams;
    public Division(String name) {
        Name = name;
        standings = new PriorityQueue<>();
        teams = new ArrayList<>();
    }
    public void addTeam(Team t) {
        teams.add(t);
        standings.add(t);
    }
    public Team getDivisionLeader() {
        int Wins = 0;
        Team Leader = null;
        for (Team t : teams) {
            if (t.getWins() > Wins) {
                Leader = t;
                Wins = t.getWins();
            } else if (t.getWins() == Wins) {
                if (t.winPctAgainst(Leader) > .5) {
                    Leader = t;
                } else if (t.divisionWinPCT() > Leader.divisionWinPCT()) {
                    Leader = t;
                }
            }
        }
        return Leader;
    }
    public boolean isDivisionLeader(Team t) {
        return t.equals(getDivisionLeader());
    }
}
