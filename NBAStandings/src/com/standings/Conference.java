package com.standings;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class Conference {
    private String Name;
    private PriorityQueue<Team> standings;
    private Team[] playoffs;
    public ArrayList<Team> teams;
    public Conference (String name) {
        this.Name = name;
        standings = new PriorityQueue<>();
        playoffs = new Team[8];
        teams = new ArrayList<>();
    }
    public void addTeam(Team t) {
        teams.add(t);
        standings.add(t);
    }
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /*Updates the standings as games are played, with the playoff teams
     * being placed in an array in reverse seed order (playoffs[0] is seed 8).
      * If teams are mathematically eliminated, then sets their elimination date.*/
    public void updateStandings(Game g) {
        PriorityQueue<Team> temp = new PriorityQueue<>();
        playoffs = new Team[8];
        temp.addAll(teams);
        standings = temp;
        for (int i = 7; i >= 0; i--) {
            playoffs[i] = standings.poll();
        }
        for (Team t : standings) {
            if (!t.isEliminated()) {
                if (t.potentialWinPct() < playoffs[0].worstCaseWinPct() || t.gamesLeft() == 0) {
                    t.Eliminate();
                    NBAStandings.setElimDates(t, g.getDate());
                } else if (t.potentialWinPct() == playoffs[0].worstCaseWinPct()) {
                    if (t.winPctAgainst(playoffs[0]) < .5) {
                        t.Eliminate();
                        NBAStandings.setElimDates(t, g.getDate());
                    } else if (t.winPctAgainst(playoffs[0]) == .5) {
                        if (playoffs[0].getDivision().getDivisionLeader() == playoffs[0]) {
                            t.Eliminate();
                            NBAStandings.setElimDates(t, g.getDate());
                        } else if (t.getDivision().equals(playoffs[0].getDivision())) {
                            if (playoffs[0].worstCaseDivisionWinPCT() < t.potentialDivisionWinPCT()) {
                                t.Eliminate();
                                NBAStandings.setElimDates(t, g.getDate());
                            } else if (playoffs[0].worstCaseDivisionWinPCT() == t.potentialDivisionWinPCT()) {
                                if (playoffs[0].worstCaseConferenceWinPCT() < t.potentialConferenceWinPCT()) {
                                    t.Eliminate();
                                    NBAStandings.setElimDates(t, g.getDate());
                                }
                            }
                        } else if (playoffs[0].worstCaseConferenceWinPCT() > t.potentialConferenceWinPCT()) {
                            t.Eliminate();
                            NBAStandings.setElimDates(t, g.getDate());
                        }
                    }
                }
            }
        }
    }
    /*Sets all the teams that makes the playoffs elimination date as playoffs. */
    public void setPlayoffs() {
        for (Team t : getTeams()) {
            if (!NBAStandings.getTeam(t.getName()).isEliminated()) {
                NBAStandings.setElimDates(t, "Playoffs");
            }
        }
    }

    public String getName() {
        return Name;
    }
    public Team[] getPlayoffs() {
        return playoffs;
    }
}
