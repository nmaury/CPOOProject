package com.city.builder.game.model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBlock {
    Block b;
    @BeforeEach
    public void setUp(){
    List<Tile> tiles = new ArrayList<Tile>();
    for(int i =0; i<100;i++){
        tiles.add(new Tile(0,0,TileType.EMPTY));
    }
    this.b = new Block(10,30,tiles,"Jean","game 25");
}
@Test
    public void TestGetScore(){
    assertEquals(this.b.getScore(),10);
}
    @Test
    public void TestSetScore(){
        this.b.setScore(50);
        assertEquals(this.b.getScore(),50);
    }
    @Test
    public void TestGetScoreLimit(){
        assertEquals(this.b.getScore_limit(),30);
    }
    @Test
    public void TestSetScoreLimit(){
    this.b.setScore_limit(40);
        assertEquals(this.b.getScore_limit(),40);
    }
    @Test
    public void TestGetTiles(){
        List<Tile> testtiles = new ArrayList<Tile>();
        for(int i=0;i<100;i++){
            testtiles.add(new Tile(0,0,TileType.EMPTY));
        }
        for(int i = 0;i<this.b.getTiles().size();i++) {
            assertEquals(this.b.getTiles().get(i).getLoct_x(), testtiles.get(i).getLoct_x());
            assertEquals(this.b.getTiles().get(i).getLoct_y(), testtiles.get(i).getLoct_y());
            assertEquals(this.b.getTiles().get(i).getType(), testtiles.get(i).getType());
        }
    }
    @Test
    public void TestSetTiles(){
        List<Tile> testtiles = new ArrayList<Tile>();
        for(int i=0;i<99;i++){
            testtiles.add(new Tile(0,0,TileType.EMPTY));
        }
        testtiles.add(new Tile(8,8,TileType.TREE));
        this.b.setTiles(testtiles);
        for(int i = 0;i<this.b.getTiles().size();i++) {
            assertEquals(this.b.getTiles().get(i).getLoct_x(), testtiles.get(i).getLoct_x());
            assertEquals(this.b.getTiles().get(i).getLoct_y(), testtiles.get(i).getLoct_y());
            assertEquals(this.b.getTiles().get(i).getType(), testtiles.get(i).getType());
        }
    }
    @Test
    public void TestGetPlayername(){
    assertEquals(this.b.getPlayername(),"Jean");
    }
    @Test
    public void TestSetPlayername(){
    this.b.setPlayername("Pierre");
        assertEquals(this.b.getPlayername(),"Pierre");
    }
    @Test
    public void TestGetGamename(){
        assertEquals(this.b.getGamename(),"game 25");
    }
    @Test
    public void TestSetGamename(){
    this.b.setGamename("game difficile");
        assertEquals(this.b.getGamename(),"game difficile");
    }

}
