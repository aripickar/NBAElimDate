package com.standings;
import java.io.*;
import java.util.HashMap;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class NBAStandings {
    private static HashMap<String, Team> Teams;
    private static HashMap<String, Division> Divisions;
    private static HashMap<String, Conference> Conferences;
    private static HashMap<Team, String> ElimDates;
    public NBAStandings() {
    }
    /* Gets the teams in the TeamNames.csv file, and adds them to the
    appropriate conference and division, and creates a Team instance.
     */
    public static void addteams() throws FileNotFoundException{
        Divisions = new HashMap<>();
        Conferences = new HashMap<>();
        ElimDates = new HashMap<>();
        Teams = new HashMap<>();
        try {
            File teams = new File("TeamNames.csv");
            InputStream input = new FileInputStream(teams);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            br.readLine();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] details = line.split(",");
                //System.out.println(details[0]);
                //System.out.println(details[1]);
                if (!Divisions.containsKey(details[1])) {
                    Divisions.put(details[1], new Division(details[1]));
                }
                if (!Conferences.containsKey(details[2])) {
                    Conferences.put(details[2], new Conference(details[2]));
                }
                Team t = new Team(details[0], Divisions.get(details[1]), Conferences.get(details[2]));
                Teams.put(details[0], t);
                //PlayoffTeams.put(details[0], t);
                Divisions.get(details[1]).addTeam(t);
                Conferences.get(details[2]).addTeam(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Teams not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
    /**Reads through the list of games and plays them 1 by 1, adding the teams and their
     * elimination dates as they go.*/
    public static void playGames() throws FileNotFoundException {
        try {
            File teams = new File("NBAScores.csv");
            InputStream input = new FileInputStream(teams);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            br.readLine();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                Game g = new Game(line.split(","));
                getTeam(g.getHomeTeam()).PlayGame(g);
                getTeam(g.getAwayTeam()).PlayGame(g);
                if (getTeam(g.getHomeTeam()).gamesLeft() < 21) {
                    getConference("West").updateStandings(g);
                    getConference("East").updateStandings(g);
                }
            }
            getConference("West").setPlayoffs();
            getConference("East").setPlayoffs();

        } catch (FileNotFoundException e) {
            System.out.println("Teams not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
    public static Team getTeam(String s) {
        return Teams.get(s);
    }
    public static Division getDivision (String t) {
        return Divisions.get(t);
    }
    public static Conference getConference (String t) {
        return Conferences.get(t);
    }
    /* Sets the elimination date for a team, or sets playoffs
    * as the elimination date if the team makes the playoffs.*/
    public static void setElimDates(Team t, String date) {
        //PlayoffTeams.remove(t);
        System.out.print(t.getName() + ", " + date + "\n");
        ElimDates.put(t, date);
    }

    public static Conference getOtherConference(String t) {
        if (t == "Eastern") {
            return Conferences.get("Western");
        } else {
            return Conferences.get("Eastern");
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        try {
            addteams();
            playGames();

            PrintStream o = new PrintStream(new File("EliminationDates.csv"));
            System.setOut(o);

            System.out.print("team_name, elimination_date \n");
            for (Team t : ElimDates.keySet()) {
                System.out.print(t.getName() + ", " + ElimDates.get(t) + "\n");
            }
        } catch (FileNotFoundException f) {
        } catch (IOException e) {
        }

    }
}
