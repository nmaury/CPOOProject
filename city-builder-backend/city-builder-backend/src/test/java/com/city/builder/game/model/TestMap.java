package com.city.builder.game.model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMap {
    Map mbdx;
    Map mlf;
    Map mrns;
    Map mrand;
    @BeforeEach
    public void setUp() throws IOException, ParseException {
        this.mbdx = new Map("Bordeaux");
        this.mlf = new Map("LaFleche");
        this.mrns = new Map("Rennes");
        this.mrand = new Map("Random");
    }
    @Test
    public void TestGetPieces(){
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PieceType.HOUSE));
        for(int i = 0; i<this.mbdx.getPieces().size();i++){
            assertEquals(this.mbdx.getPieces().get(i).getType(),pieces.get(i).getType());
        }
    }
    @Test
    public void TestSetPieces(){
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PieceType.CIRCUS));
        this.mbdx.setPieces(pieces);
        for(int i = 0; i<this.mbdx.getPieces().size();i++){
            assertEquals(this.mbdx.getPieces().get(i).getType(),pieces.get(i).getType());
        }
    }
    @Test
    public void TestGetOnePiece(){
        Piece testpiece = new Piece(PieceType.HOUSE);
        assertEquals(this.mbdx.getOnePiece(0).getType(),testpiece.getType());
    }
    @Test
    public void TestGetTiles(){
        List<Tile> tiles = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j = 0;j<10;j++) {
                tiles.add(new Tile(i,j,TileType.EMPTY));
            }
        }
        for(int i = 0; i<this.mbdx.getTiles().size();i++){
            assertEquals(this.mbdx.getTiles().get(i).getLoct_x(),tiles.get(i).getLoct_x());
            assertEquals(this.mbdx.getTiles().get(i).getLoct_y(),tiles.get(i).getLoct_y());
        }
}
    @Test
    public void TestSetTiles(){
        List<Tile> tiles = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j = 0;j<10;j++) {
                tiles.add(new Tile(i,j,TileType.EMPTY));
            }
        }
        this.mbdx.setTiles(tiles);
        for(int i = 0; i<this.mbdx.getTiles().size();i++){
            assertEquals(this.mbdx.getTiles().get(i).getLoct_x(),tiles.get(i).getLoct_x());
            assertEquals(this.mbdx.getTiles().get(i).getLoct_y(),tiles.get(i).getLoct_y());
            assertEquals(this.mbdx.getTiles().get(i).getType(),tiles.get(i).getType());
        }
    }
    @Test
    public void TestGetOneTile(){
        Tile testtile = new Tile(0,0,TileType.TREE);
        assertEquals(this.mbdx.getOneTile(0).getLoct_x(),testtile.getLoct_x());
        assertEquals(this.mbdx.getOneTile(0).getLoct_y(),testtile.getLoct_y());
        assertEquals(this.mbdx.getOneTile(0).getType(),testtile.getType());
    }
    @Test
    public void TestSetOneTile(){
        Tile testtile = new Tile(0,0,TileType.EMPTY);
        this.mbdx.setOneTile(0,testtile);
        assertEquals(this.mbdx.getTiles().get(0).getLoct_x(),testtile.getLoct_x());
        assertEquals(this.mbdx.getTiles().get(0).getLoct_y(),testtile.getLoct_y());
        assertEquals(this.mbdx.getTiles().get(0).getType(),testtile.getType());
    }
    @Test
    public void TestAddPiece(){
        List<Piece> pieces= this.mbdx.getPieces();
        Piece testpiece = new Piece(PieceType.HOUSE);
        pieces.add(testpiece);
        this.mbdx.addPiece(testpiece);
        assertEquals(this.mbdx.getPieces(),pieces);
    }
    @Test
    public void TestName(){
        assertEquals(this.mbdx.getName(),"Bordeaux");
    }
    @Test
    public void TestGetScore(){
        assertEquals(this.mbdx.getScore(),0);
    }
    @Test
    public void TestSetScore(){
        this.mbdx.setScore(10);
        assertEquals(this.mbdx.getScore(),10);
    }
    @Test
    public void TestGetScoreLimit(){
        assertEquals(this.mbdx.getScore_limit(),10);
    }
    @Test
    public void TestSetScoreLimit(){
        this.mbdx.setScore_limit(30);
        assertEquals(this.mbdx.getScore_limit(),30);
    }
    @Test
    public void TestGetTurn(){
        assertEquals(this.mbdx.getTurn(),1);
    }
    @Test
    public void TestSetTurn(){
        this.mbdx.setTurn(2);
        assertEquals(this.mbdx.getTurn(),2);
    }
}
