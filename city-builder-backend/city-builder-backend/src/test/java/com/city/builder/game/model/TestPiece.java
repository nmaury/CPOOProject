package com.city.builder.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPiece {
    Piece p;
    @BeforeEach
    public void setUp(){
        this.p = new Piece(PieceType.HOUSE);
    }
    @Test
    public void TestGetType(){
        assertEquals(this.p.getType(),PieceType.HOUSE);
    }
    @Test
    public void TestSetType(){
        this.p.setType(PieceType.CIRCUS);
        assertEquals(this.p.getType(),PieceType.CIRCUS);
    }
}
