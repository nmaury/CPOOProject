package com.city.builder.game.model;

public class Tile {
    private int loct_x;
    private int loct_y;
    private TileType type;

    public Tile(final int loct_x, final int loct_y, final TileType type) {
        this.loct_x = loct_x;
        this.loct_y = loct_y;
        this.type = type;
    }

    public int getLoct_x() {
        return loct_x;
    }

    public void setLoct_x(final int loct_x) {
        this.loct_x = loct_x;
    }

    public int getLoct_y() {
        return loct_y;
    }

    public void setLoct_y(final int loct_y) {
        this.loct_y = loct_y;
    }

    public TileType getType() {
        return type;
    }

    public void setType(final TileType type) {
        this.type = type;
    }
}
