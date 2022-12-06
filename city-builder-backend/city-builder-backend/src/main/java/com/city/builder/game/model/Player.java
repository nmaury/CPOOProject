package com.city.builder.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String user_name;
    private List<Game> games;


    public Player(final String user_name, final int id) {
        this.id = id;
        this.user_name = user_name;
        games = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(final String user_name) {
        this.user_name = user_name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(final Game g) {
        this.games.add(g);
    }

    public void setGames(final List<Game> games) {
        this.games = games;
    }
}
