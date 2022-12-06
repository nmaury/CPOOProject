package com.city.builder.game.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGame {
    Game g;
    @BeforeEach
    public void setUp() throws IOException, ParseException {
        this.g= new Game("game 1",1,"Jean", "Bordeaux");
    }
    @Test
    public void TestGetId(){
        assertEquals(this.g.getId(),1);
    }
    @Test
    public void TestSetId(){
        this.g.setId(2);
        assertEquals(this.g.getId(),2);
    }
    @Test
    public void TestGetGamename(){
        assertEquals(this.g.getGame_name(),"game 1");
    }
    @Test
    public void TestSetGamename(){
        this.g.setGame_name("game 2");
        assertEquals(this.g.getGame_name(),"game 2");
    }
    @Test
    public void TestGetPlayer(){
        assertEquals(this.g.getPlayer(),"Jean");
    }
    @Test
    public void TestSetPlayer(){
        this.g.setPlayer("Pierre");
        assertEquals(this.g.getPlayer(),"Pierre");
    }
    @Test
    public void TestGetMap(){
        assertEquals(this.g.getMap().getName(),"Bordeaux");
    }

    @Test
    public void TestEnd() throws IOException, ParseException {
        String res = this.g.end("Bordeaux",600,"p2").toString();
        JSONParser jsonp = new JSONParser();
        List<JSONObject> jsonO = (List<JSONObject>) jsonp.parse(new FileReader("src/main/java/com/city/builder/scores/scores.json"));
        assertEquals(res,jsonO.toString());
    }


}
