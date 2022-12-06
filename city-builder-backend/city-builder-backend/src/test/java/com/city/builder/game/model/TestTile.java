package com.city.builder.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTile {
    Tile t;
    @BeforeEach
    public void setUp(){
        this.t = new Tile(0,0,TileType.TREE);
    }
    @Test
    public void TestSetLoct(){
        this.t.setLoct_x(1);
        this.t.setLoct_y(1);
        assertEquals(this.t.getLoct_x(),1);
        assertEquals(this.t.getLoct_y(),1);
    }
    @Test
    public void TestSetType(){
        this.t.setType(TileType.EMPTY);
        assertEquals(this.t.getType(),TileType.EMPTY);
    }
}
