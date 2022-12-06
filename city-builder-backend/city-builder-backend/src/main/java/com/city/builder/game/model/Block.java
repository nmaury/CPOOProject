package com.city.builder.game.model;

import java.util.List;

public class Block {
    private int score;
    private int score_limit;
    private List<Tile> tiles;
    private String playername;
    private String gamename;
    public Block(final int score, final int score_limit, final List<Tile> tiles, final String playername, final String gamename) {
        this.score = score;
        this.score_limit = score_limit;
        this.tiles = tiles;
        this.gamename = gamename;
        this.playername = playername;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public int getScore_limit() {
        return score_limit;
    }

    public void setScore_limit(final int score_limit) {
        this.score_limit = score_limit;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(final List<Tile> tiles) {
        this.tiles = tiles;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(final String playername) {
        this.playername = playername;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(final String gamename) {
        this.gamename = gamename;
    }
}
