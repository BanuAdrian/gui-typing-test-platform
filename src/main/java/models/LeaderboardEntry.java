package models;

import models.enums.LeaderboardType;

public class LeaderboardEntry implements Listable {
    private int place;
    private String username;
    private int value;
    private String wpmOrScore;

    public LeaderboardEntry(int place, String username, int value, LeaderboardType leaderboardType) {
        this.place = place;
        this.username = username;
        this.value = value;
        if (leaderboardType == LeaderboardType.SCORE) {
            this.wpmOrScore = "points";
        } else {
            this.wpmOrScore = "WPM";
        }
    }

    @Override
    public String textInList() {
        return place + ". " + username + " (" + value + " " + wpmOrScore + ")";
    }

    @Override
    public String textWhenSelected() {
        return username + " is number " + place + " in the leaderboard with " + value + " " + wpmOrScore;
    }
}
