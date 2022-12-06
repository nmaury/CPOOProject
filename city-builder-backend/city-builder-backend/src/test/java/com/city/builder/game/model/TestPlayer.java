package com.city.builder.game.model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPlayer {
    Player p;
    @BeforeEach
    public void setUp(){
        this.p = new Player("Jean",0);
    }
    @Test
    public void TestGetId(){
        assertEquals(this.p.getId(),0);
    }
    @Test
    public void TestSetId(){
        this.p.setId(1);
        assertEquals(this.p.getId(),1);
    }
    @Test
    public void TestGetUsername(){
        assertEquals(this.p.getUser_name(),"Jean");
    }
    @Test
    public void TestSetUsername(){
        this.p.setUser_name("Pierre");
        assertEquals(this.p.getUser_name(),"Pierre");
    }
    @Test
    public void TestGames() throws IOException, ParseException {
        assertNotNull(this.p.getGames());
        Game gametest = new Game("game 1",1,"Jean","Bordeaux");
        this.p.addGame(gametest);
        assertEquals(this.p.getGames().get(0),gametest);
    }
    @Test
    public void TestSetGames() throws IOException, ParseException {
        List<Game> testgames = new ArrayList<>();
        Game g1 = new Game("game 1",1,"Jean","Bordeaux");
        Game g2 = new Game("game 2",2,"Jean","Random");
        testgames.add(g1);
        testgames.add(g2);
        this.p.setGames(testgames);
        for(int i=0;i<this.p.getGames().size();i++){
            assertEquals(this.p.getGames().get(i),testgames.get(i));
        }
    }
}
