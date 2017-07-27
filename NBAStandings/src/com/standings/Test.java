package com.standings;


import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by AaronPickar on 7/25/17.
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            NBAStandings.main(args);
            Scanner sc = new Scanner(System.in);
            String correct = "Brooklyn Nets, 3/6/17\n" +
                    "Los Angeles Lakers, 3/17/17\n" +
                    "Phoenix Suns, 3/21/17\n" +
                    "Orlando Magic, 3/28/17\n" +
                    "Philadelphia 76ers, 3/29/17\n" +
                    "New York Knicks, 3/29/17\n" +
                    "Sacramento Kings, 3/29/17\n" +
                    "Minnesota Timberwolves, 4/1/17\n" +
                    "Dallas Mavericks, 4/1/17\n" +
                    "New Orleans Pelicans, 4/4/17\n" +
                    "Detroit Pistons, 4/7/17\n" +
                    "Charlotte Hornets, 4/8/17\n" +
                    "Denver Nuggets, 4/9/17\n" +
                    "Miami Heat, 4/12/17\n" +
                    "Oklahoma City Thunder, Playoffs\n" +
                    "Portland Trail Blazers, Playoffs\n" +
                    "Utah Jazz, Playoffs\n" +
                    "Golden State Warriors, Playoffs\n" +
                    "LA Clippers, Playoffs\n" +
                    "Houston Rockets, Playoffs\n" +
                    "Memphis Grizzlies, Playoffs\n" +
                    "San Antonio Spurs, Playoffs\n" +
                    "Boston Celtics, Playoffs\n" +
                    "Toronto Raptors, Playoffs\n" +
                    "Chicago Bulls, Playoffs\n" +
                    "Cleveland Cavaliers, Playoffs\n" +
                    "Indiana Pacers, Playoffs\n" +
                    "Milwaukee Bucks, Playoffs\n" +
                    "Atlanta Hawks, Playoffs\n" +
                    "Washington Wizards, Playoffs";
            String[] records = correct.split("\n");
            int i = 0;
            while (sc.hasNextLine()) {
                if (!sc.nextLine().equals(records[i])) {
                    break;
                }
            }
        } catch (FileNotFoundException f) {
            System.out.println("FileNotFound");
        }
    }


}
