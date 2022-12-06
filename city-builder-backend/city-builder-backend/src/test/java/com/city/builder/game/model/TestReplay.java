package com.city.builder.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestReplay {
    Replay r;
    List<Tile> testtiles;
    Block testblock;
    @BeforeEach
    public void setUp(){
        this.r = new Replay();
        this.testtiles = new ArrayList<>();
        for(int i = 0; i<100;i++){
            this.testtiles.add(new Tile(0,0,TileType.EMPTY));
        }
        this.testblock = new Block(10,30,testtiles,"Jean","game 1");
        this.r.addGame(this.testblock);
    }

    @Test
    public void TestaddGame(){
        assertEquals(this.r.getGamelist().get(0).get(0),testblock);
        assertEquals(this.r.getOneGameList(0).get(0),testblock);
    }
    @Test
    public void TestAddBlock(){
        Block b1 = new Block(20,60,testtiles,"Pierre","game 2");
        this.r.addBlock(0,b1);
        assertEquals(this.r.getOneGameList(0).get(1),b1);
    }
    @Test
    public void TestSetGameList(){
        List<List<Block>> testgameslist = new ArrayList<>();
        List<Block> testblocklist = new ArrayList<>();
        testblocklist.add(this.testblock);
        testgameslist.add(testblocklist);
        this.r.setGamelist(testgameslist);
        assertEquals(this.r.getGamelist(),testgameslist);
    }
}
