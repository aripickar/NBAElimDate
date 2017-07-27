package com.standings;
import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class Team implements Comparable<Team>{
    private String Name;
    private Conference Conference;
    private Division Division;
    private int Wins;
    private int Losses;
    private int NetPoints;
    private int ConferenceWins;
    private int ConferenceGames;
    private int DivisionWins;
    private int DivisionGames;
    private boolean Eliminated;
    private ArrayList<Game> Games;
    /* Maps a team to an array that has [wins, games].*/
    private HashMap<Team, Integer[]> gamesAgainstTeam;


    public Team(String name, Division division, Conference conference) {
        Name = name;
        Wins = 0;
        Losses = 0;
        Games = new ArrayList<>();
        gamesAgainstTeam = new HashMap<>();
        Division = division;
        Conference = conference;
        Eliminated = false;
    }

    public String getName() {
        return Name;
    }

    public Conference getConference() {
        return Conference;
    }

    public Division getDivision() {
        return Division;
    }
    /* Takes in data from a game g, and simulates that team
    playing it.
     */
    public void PlayGame(Game g) {
        Team otherTeam;

        if (g.getHomeTeam().equals(this.getName())) {
            otherTeam = NBAStandings.getTeam(g.getAwayTeam());
        } else {
            otherTeam = NBAStandings.getTeam(g.getHomeTeam());
        }
        Games.add(g);
        if (!gamesAgainstTeam.containsKey(otherTeam) ) {
            Integer[] winsGames = new Integer[2];
            winsGames[0] = 0;
            winsGames[1] = 0;
            gamesAgainstTeam.put(otherTeam, winsGames);
        }
        gamesAgainstTeam.get(otherTeam)[1] += 1;
        if (g.HomeWon() && g.getHomeTeam().equals(getName()) ||
                !g.HomeWon() && g.getAwayTeam().equals(getName())) {
            gamesAgainstTeam.get(otherTeam)[0] += 1;
        }
        if (g.getHomeTeam().equals(getName()) && g.HomeWon()
                || g.getAwayTeam().equals(getName()) && !g.HomeWon()) {
            Wins++;
        } else {
            Losses++;
        }
        if (otherTeam.getConference().equals(getConference())) {
            if (g.HomeWon() && g.getHomeTeam().equals(getName()) ||
                    !g.HomeWon() && g.getAwayTeam().equals(getName())) {
                ConferenceWins++;
                ConferenceGames++;
            } else {
                ConferenceGames++;
            }
        }
        if (otherTeam.getDivision().equals(getDivision())) {
            if (g.HomeWon() && g.getHomeTeam().equals(getName()) ||
                    !g.HomeWon() && g.getAwayTeam().equals(getName())) {
                DivisionWins++;
                DivisionGames++;
            } else {
                DivisionGames++;
            }
        }
        if (g.getHomeTeam().equals(Name)) {
            NetPoints = g.getHomeScore() - g.getAwayScore();
        } else {
            NetPoints = g.getAwayScore() - g.getHomeScore();
    }

    }
    public double winPct() {
        return ((double) Wins) / (Math.max(Wins + Losses, 1));
    }
    public double potentialWinPct() {
        return ((double) Wins + gamesLeft()) / 82;
    }

    public double worstCaseWinPct() {
        return ((double) Wins / 82);
    }
    public double conferenceWinPCT() {
        return ((double) ConferenceWins / ConferenceGames);
    }

    public double potentialConferenceWinPCT() {
        return ((double) (52 - ConferenceGames + ConferenceWins) / 52);
    }
    public double worstCaseConferenceWinPCT() {
        return ((double) ConferenceWins / 52);
    }
    public double divisionWinPCT() {
        return ((double) DivisionWins / DivisionGames);
    }

    public double potentialDivisionWinPCT() {
        return ((double) (16 - DivisionGames + DivisionWins) / 16);
    }
    public double worstCaseDivisionWinPCT() {
        return ((double) DivisionWins / 16);
    }

    @Override
    public int compareTo(Team t) {
        if (Wins + Losses == 0) {
            return 1;
        } else if (t.Wins + t.Losses == 0) {
            return -1;
        } else {
            return WinPCTCheck(t);
        }
    }


    private int WinPCTCheck(Team t) {
        if (potentialWinPct() > t.potentialWinPct() ) {
            return -1;
        } else if(potentialWinPct() < t.potentialWinPct()) {
            return 1;
        } else {
            return CommonCheck(t);
        }
    }

    private int CommonCheck(Team t) {
        if (gamesAgainstTeam.get(t) != null) {
            int SimWins = gamesAgainstTeam.get(t)[0];
            int SimOppWins = gamesAgainstTeam.get(t)[1] - gamesAgainstTeam.get(t)[0];
            if (SimWins > SimOppWins) {
                return -1;
            } else if (SimOppWins > SimWins) {
                return 1;
            } else {
                return DivisionLeaderCheck(t);
            }
        } else {
            return DivisionLeaderCheck(t);
        }
    }

    private int DivisionLeaderCheck(Team t) {
        if (getDivision().isDivisionLeader(this) && !t.getDivision().isDivisionLeader(t)) {
            return -1;
        } else if (!getDivision().isDivisionLeader(this) && t.getDivision().isDivisionLeader(t)) {
            return 1;
        } else {
            return DivisionWLCheck(t);
        }
    }

    private int DivisionWLCheck(Team t) {
        if (getDivision() != t.getDivision()) {
            return ConferenceWLCheck(t);
        } else {
            if (divisionWinPCT() > t.divisionWinPCT()) {
                return -1;
            } else if (divisionWinPCT() < t.divisionWinPCT()) {
                 return 1;
            } else {
                return ConferenceWLCheck(t);
            }
        }
    }
    private int ConferenceWLCheck(Team t) {
        if (conferenceWinPCT() > t.conferenceWinPCT()) {
            return -1;
        } else if (conferenceWinPCT() < t.conferenceWinPCT()) {
            return 1;
        } else {
            return ownConferencePlayoffCheck(t);
        }
    }
    private int ownConferencePlayoffCheck(Team t) {
        int ownPlayWins = 0;
        int oppPlayWins = 0;
        int ownPlayGames = 0;
        int oppPlayGames = 0;
        for (Team play : getConference().getPlayoffs()) {
            ownPlayWins += gamesAgainstTeam.get(play)[0];
            oppPlayWins += t.gamesAgainstTeam.get(play)[0];
            ownPlayGames += gamesAgainstTeam.get(play)[1];
            oppPlayGames += t.gamesAgainstTeam.get(play)[1];
        }
        if (ownPlayWins / ownPlayGames > oppPlayWins / oppPlayGames) {
            return -1;
        } else if (ownPlayWins / ownPlayGames < oppPlayWins / oppPlayGames) {
            return 1;
        } else {
            return otherConferencePlayoffCheck(t);
        }
    }
    private int otherConferencePlayoffCheck(Team t) {
        int ownPlayWins = 0;
        int oppPlayWins = 0;
        int ownPlayGames = 0;
        int oppPlayGames = 0;
        for (Team play : NBAStandings.getOtherConference(getConference().getName()).getPlayoffs()) {
            ownPlayWins += gamesAgainstTeam.get(play)[0];
            oppPlayWins += t.gamesAgainstTeam.get(play)[0];
            ownPlayGames += gamesAgainstTeam.get(play)[1];
            oppPlayGames += t.gamesAgainstTeam.get(play)[1];
        }
        if (ownPlayWins / ownPlayGames > oppPlayWins / oppPlayGames) {
            return -1;
        } else if (ownPlayWins / ownPlayGames < oppPlayWins / oppPlayGames) {
            return 1;
        } else {
            return pointDiffCheck(t);
        }
    }
    private int pointDiffCheck(Team t) {
        if (getNetPoints() > t.getNetPoints()) {
            return -1;
        } else if (this.NetPoints < t.NetPoints){
            return 1;
        } else {
            if (StdRandom.uniform() < .5) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    public int getWins() {
        return Wins;
    }

    public int getLosses() {
        return Losses;
    }

    public int gamesLeft() {
        return 82 - Wins - Losses;
    }
    public void Eliminate() {
        Eliminated = true;
    }
    public boolean isEliminated() {
        return Eliminated;
    }
    public HashMap<Team, Integer[]> getGamesAgainstTeam() {
        return gamesAgainstTeam;
    }
    public int getNetPoints() {
        return NetPoints;
    }
    public double winPctAgainst(Team t) {
        return (double) gamesAgainstTeam.get(t)[0] / gamesAgainstTeam.get(t)[1];
    }
}
