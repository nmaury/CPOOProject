package com.city.builder.game.model;


import java.util.ArrayList;
import java.util.List;

public class Replay {
    private List<List<Block>> gamelist;

    public Replay() {
        gamelist = new ArrayList<>();
    }
    public void addGame(final Block b) {
        final List<Block> l = new ArrayList<>();
        l.add(b);
        gamelist.add(l);
    }
    public void addBlock(final int gameid, final Block b) {
        gamelist.get(gameid).add(b);
    }

    public List<List<Block>> getGamelist() {
        return gamelist;
    }
    public List<Block> getOneGameList(final int id) {
        return this.gamelist.get(id);
    }

    public void setGamelist(final List<List<Block>> gamelist) {
        this.gamelist = gamelist;
    }
}
